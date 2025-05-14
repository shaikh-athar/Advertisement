package com.gov.Advertisments.ServiceImple;

import com.gov.Advertisments.Model.*;
import com.gov.Advertisments.Model.Enums.BookingStatus;
import com.gov.Advertisments.Model.Enums.MaintainerStatus;
import com.gov.Advertisments.Model.Enums.MaintenanceStatus;
import com.gov.Advertisments.Model.Request.MaintenanceRequest;
import com.gov.Advertisments.Repository.*;
import com.gov.Advertisments.Service.MaintenanceService;
import com.gov.Advertisments.ServiceImple.OtherImple.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MaintenanceServiceImple implements MaintenanceService {
    @Autowired
    MaintenanceRepo maintenanceRepository;
    @Autowired
    ScreenRepo screenRepository;
    @Autowired
    UserRepo userRepository;
    @Autowired
    BookingRepo bookingRepository;
    @Autowired
    MaintainerRepo maintainerRepository;

//        DataIntegrityViolationException if filed data duplication occur
    public String addMaintenance(MaintenanceRequest maintenanceRequest) {
        try {
            Maintenance maintenance = new Maintenance();
            maintenance.setIssue(maintenanceRequest.getIssue());
            maintenance.setStatus(MaintenanceStatus.DAMAGE);
            maintenance.setComplaintId(IdGenerator.getId(6));
            maintenance.setMaintenanceTime(LocalDateTime.now());

            return CommonMaintenance(maintenance, maintenanceRequest);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Error While Adding Maintenance!";
        }
    }

    public String updateMaintenance(String complaintId, MaintenanceRequest maintenanceRequest) {
        try {
            Optional<Maintenance> existing = maintenanceRepository.findByComplaintId(complaintId);
            if (existing.isEmpty()) {
                return "Maintenance record not found!";
            }
            Maintenance maintenance = existing.get();
            maintenance.setIssue(maintenanceRequest.getIssue());

            return CommonMaintenance(maintenance, maintenanceRequest);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Error While Updating Maintenance!";
        }
    }

    public String screenRepaired(long id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            return "Maintenance record not found!";
        }

        Maintenance maintenanceRecord = maintenance.get();
        Screen repairedScreen = maintenanceRecord.getScreen();

        // Mark the screen as active
        repairedScreen.setIsActive(true);
        screenRepository.save(repairedScreen);

        // Update maintenance status
        maintenanceRecord.setStatus(MaintenanceStatus.FIXED);
        maintenanceRepository.save(maintenanceRecord);

        // Find all bookings originally assigned to this screen
        List<Booking> bookings = bookingRepository.findByScreenId(repairedScreen.getId());

        for (Booking booking : bookings) {
            if (!booking.getScreen().getId().equals(repairedScreen.getId())) {
                continue; // Skip bookings that were already moved to another screen
            }

            // Find the nearest available screen for the booking
            Screen nearestScreen = findNearestScreen(repairedScreen.getLatitude(), repairedScreen.getLongitude());

            if (nearestScreen != null) {
                // Shift booking to the nearest available screen (does not revert back to the repaired one)
                booking.setScreen(nearestScreen);
                bookingRepository.save(booking);
            } else {
                // If no available screen is found, cancel the booking
                booking.setStatus(BookingStatus.CANCELLED);
                bookingRepository.save(booking);
            }
        }

        return "Screen repaired. Existing bookings remain on their assigned screens.";
    }

    private String CommonMaintenance(Maintenance maintenance, MaintenanceRequest maintenanceRequest) {
        // Fetch the screen by ID
        Optional<Screen> screen = screenRepository.findById(maintenanceRequest.getScreenId());
        if (screen.isEmpty()) {
            return "Screen not found!";
        }

        Screen affectedScreen = screen.get();
        affectedScreen.setIsActive(Boolean.FALSE);
        screenRepository.save(affectedScreen);

        // Find the nearest available screen for shifting services
        Screen nearestScreen = findNearestScreen(affectedScreen.getLatitude(), affectedScreen.getLongitude());
        if (nearestScreen == null) {
            return "No available screens nearby to shift current services!";
        }

        // Find all bookings originally assigned to this screen
        List<Booking> bookings = bookingRepository.findByScreenId(affectedScreen.getId());

        for (Booking booking : bookings) {
            // Shift the booking to the nearest available screen
            booking.setScreen(nearestScreen);
            bookingRepository.save(booking);
        }

        // Set maintenance for the affected screen
        maintenance.setScreen(affectedScreen);

        // Find the admin responsible for this maintenance
        Optional<User> admin = userRepository.findAdminById(maintenanceRequest.getAdminId());
        if (admin.isEmpty()) {
            return "Admin not found!";
        }
        maintenance.setAdmin(admin.get());

        // Find the nearest available maintainer
        Maintainer nearestMaintainer = findNearestMaintainer(affectedScreen.getLatitude(), affectedScreen.getLongitude());
        if (nearestMaintainer == null) {
            return "No Maintainer Available within a 15km range.";
        }
        maintenance.setMaintainer(nearestMaintainer);

        // Save the maintenance record
        maintenanceRepository.save(maintenance);

        return "Maintenance record saved successfully!! \nMaintainer Name: " + nearestMaintainer.getName() +
                "\nCurrent service has been shifted to the nearest screen at: " + nearestScreen.getLocation() +
                " (Serial No: " + nearestScreen.getSerialNo() + ")";
    }

    public Maintainer findNearestMaintainer(double screenLat, double screenLong) {
        List<Maintainer> maintainers = maintainerRepository.findAll();

        if (maintainers.isEmpty()) {
            System.out.println("No maintainers available.");
            return null;
        }

        List<Double> distances = new ArrayList<>();
        for (Maintainer maintainer : maintainers) {
            double distance = calculateDistance(screenLat, screenLong, maintainer.getLatitude(), maintainer.getLongitude());
            distances.add(distance);
        }

        Collections.sort(distances);
        System.out.println(distances);

        while (!distances.isEmpty()) {
            int min = distances.indexOf(Collections.min(distances));
            Maintainer nearestMaintainer = maintainers.get(min);

            if(distances.get(0) < 15) {
                if (nearestMaintainer.getStatus() != MaintainerStatus.BUSY && nearestMaintainer.getStatus() != MaintainerStatus.INACTIVE) {
                    System.out.println("Nearest Available Maintainer: " + nearestMaintainer.getName());
                    return nearestMaintainer;
                }
            }
            else {
                return null;
            }
            maintainers.remove(min);
            distances.remove(min);
        }

        System.out.println("No available maintainers found.");
        return null;
    }

    public Screen findNearestScreen(double activeScreenLat, double activeScreenLong) {
        List<Screen> screens = screenRepository.findAll();

        if (screens.isEmpty()) {
            System.out.println("No screens available.");
            return null;
        }

        List<Double> distances = new ArrayList<>();
        for (Screen screen : screens) {
            double distance = calculateDistance(activeScreenLat, activeScreenLong, screen.getLatitude(), screen.getLongitude());
            distances.add(distance);
        }

        while (!distances.isEmpty()) {
            int min = distances.indexOf(Collections.min(distances));
            Screen nearestScreen = screens.get(min);

            if (nearestScreen.getIsActive() == Boolean.TRUE ) {
                System.out.println("Nearest Available Screen: " + nearestScreen.getSerialNo() + " At Location " + nearestScreen.getLocation());
                return nearestScreen;
            }

            screens.remove(min);
            distances.remove(min);
        }

        System.out.println("No available screens found.");
        return null;
    }

    double haversine(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
    double calculateDistance(double startLat, double startLong, double endLat, double endLong) {

        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371 * c;
    }

}
