package com.gov.Advertisments.ServiceImple;

import com.gov.Advertisments.Model.*;
import com.gov.Advertisments.Model.Enums.BookingStatus;
import com.gov.Advertisments.Model.Enums.PaymentStatus;
import com.gov.Advertisments.Model.Request.BookingRequest;
import com.gov.Advertisments.Repository.*;
import com.gov.Advertisments.Service.BookingService;
import com.gov.Advertisments.ServiceImple.OtherImple.HourGenerator;
import com.gov.Advertisments.ServiceImple.OtherImple.IdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookingServiceImple implements BookingService {

    @Autowired
    BookingRepo bookingRepository;
    @Autowired
    UserRepo userRepository;
    @Autowired
    ScreenRepo screenRepository;
    @Autowired
    PaymentRepo paymentRepository;
    @Autowired
    MaintenanceRepo maintenanceRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(String bookingId) {
        return bookingRepository.findByBookingId(bookingId);
    }

    public List<Booking> getBookingByStatus(String status){
        return bookingRepository.findByStatus(BookingStatus.valueOf(status));
    }

    //    DataIntegrityViolationException if filed data duplication occur
    public String addBooking(BookingRequest bookingRequest) {
        try {
            // Validate start and end times
            if (!bookingRequest.getStartTime().isBefore(bookingRequest.getEndTime())) {
                return "End Date must be after Start Date.";
            }

            // Check if slot is available
            boolean slot = bookingRepository.findBookingSlot(
                    bookingRequest.getStartTime(), bookingRequest.getEndTime()
            );
            if (slot) {
                String suggestedSlot = findOrSuggestAvailableSlot(bookingRequest.getStartTime(), bookingRequest.getEndTime());
                return suggestedSlot;
            }

            Optional<User> advertiser = userRepository.findByName(bookingRequest.getAdvertiserName());
            if (advertiser.isEmpty()) {
                return "Advertiser not found!";
            }

            Optional<Screen> screenOptional = screenRepository.findById(bookingRequest.getScreenIds());
            if (screenOptional.isEmpty()) {
                return "Screen with ID " + bookingRequest.getScreenIds() + " is not available!";
            }

            Screen screen = screenOptional.get();
            if (!screen.getIsActive()) {
                Optional<Maintenance> maintenance = maintenanceRepository.findByScreenId(screen.getId());
                if (maintenance.isPresent()) {
                    LocalDateTime maintenanceDone = maintenance.get().getMaintenanceTime().plusHours(2);
                    return "Screen with ID " + screen.getId() + " is in Maintenance Mode. It will be available after " + maintenanceDone;
                }
            }

            int amount = HourGenerator.getHours(bookingRequest.getStartTime(), bookingRequest.getEndTime()) * 100;

            Booking booking = new Booking();
            booking.setBookingId(IdGenerator.getId(5));
            booking.setStartTime(bookingRequest.getStartTime());
            booking.setEndTime(bookingRequest.getEndTime());
            booking.setStatus(BookingStatus.PENDING);
            booking.setAmount(amount);
            booking.setAdvertiser(advertiser.get());
            booking.setScreen(screen);

            bookingRepository.save(booking);
            return "Booking Saved. You have to pay " + amount + " for this booking.";
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Error While Booking!";
        }
    }

    public String updateBooking(BookingRequest bookingRequest, String bookingId) {
        try {
            if (!bookingRequest.getStartTime().isBefore(bookingRequest.getEndTime())) {
                return "End Date must be after Start Date.";
            }

            if (bookingId == null) {
                return "Booking ID is required for update.";
            }

            Optional<Booking> existingBooking = bookingRepository.findByBookingId(bookingId);
            if (existingBooking.isEmpty()) {
                return "Booking not found!";
            }

            Booking booking = existingBooking.get();

            boolean isSlotTaken = bookingRepository.SlotAvailableForUpdate(
                    bookingRequest.getStartTime(),
                    bookingRequest.getEndTime(),
                    booking.getBookingId()
            );

            if (isSlotTaken) {
                return "This slot is already booked! Please select a different time.";
            }

            int oldAmount = booking.getAmount();

            Optional<Screen> screenOptional = screenRepository.findById(bookingRequest.getScreenIds());
            if (screenOptional.isEmpty()) {
                return "Screen with ID " + bookingRequest.getScreenIds() + " is not available!";
            }

            Screen screen = screenOptional.get();
            if (!screen.getIsActive()) {
                Optional<Maintenance> maintenance = maintenanceRepository.findByScreenId(screen.getId());
                if (maintenance.isPresent()) {
                    LocalDateTime maintenanceDone = maintenance.get().getMaintenanceTime().plusHours(2);
                    return "Screen with ID " + screen.getId() + " is in Maintenance Mode. It will be available after " + maintenanceDone;
                }
            }

            int amount = HourGenerator.getHours(bookingRequest.getStartTime(), bookingRequest.getEndTime()) * 100;

            booking.setStartTime(bookingRequest.getStartTime());
            booking.setEndTime(bookingRequest.getEndTime());
            booking.setScreen(screen);

            Optional<Payment> payment = paymentRepository.findByBooking(booking);

            if (amount > oldAmount) {
                if (payment.isPresent()) {
                    Payment p = payment.get();
                    p.setStatus(PaymentStatus.PENDING);
                    paymentRepository.save(p);
                }
                booking.setStatus(BookingStatus.PENDING);
                booking.setAmount(amount);
                bookingRepository.save(booking);
                return "Booking Updated. You have to pay an additional amount of " + (amount - oldAmount);
            } else if (amount < oldAmount) {
                if (payment.isPresent()) {
                    Payment p = payment.get();
                    p.setStatus(PaymentStatus.SUCCESS);
                    paymentRepository.save(p);
                }
                booking.setStatus(BookingStatus.CONFIRMED);
                booking.setAmount(amount);
                bookingRepository.save(booking);
                return "Booking Updated. Your Refund Amount is " + (oldAmount - amount);
            } else {
                bookingRepository.save(booking);
                return "Booking Updated.";
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return "Error While Updating Booking!";
        }
    }


    @Transactional
    public String cancelBooking(String bookingId) {
        bookingRepository.cancelBooking(bookingId);
        return "Booking Cancel";
    }

    public String findOrSuggestAvailableSlot(LocalDateTime bookingStart, LocalDateTime bookingEnd) {
        if (isScreenOffTime(bookingStart) || isScreenOffTime(bookingEnd)) {
            return "Screen is off between 10 PM and 8 AM.";
        }

        List<Booking> bookings = bookingRepository.findAllByOrderByStartTimeAsc();
        List<String> availableSlots = new ArrayList<>();

        boolean isSlotAvailable = true;

        LocalDateTime DaystartTime = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0);

        for (Booking booking : bookings) {
            LocalDateTime bookingStartTime = booking.getStartTime();
            LocalDateTime bookingEndTime = booking.getEndTime();

            if (DaystartTime.isBefore(bookingStartTime)) {
                if (!isScreenOffTime(DaystartTime) && !isScreenOffTime(bookingStartTime)) {
                    availableSlots.add("Start: " + DaystartTime + ", End: " + bookingStartTime);
                }
            }

            if (!(bookingEnd.isBefore(bookingStartTime) || bookingStart.isAfter(bookingEndTime))) {
                isSlotAvailable = false;
            }

            DaystartTime = bookingEndTime;
        }

        LocalDateTime possibleStart = DaystartTime;
        LocalDateTime possibleEnd = possibleStart.plusHours(Duration.between(bookingStart, bookingEnd).toHours());
        if (!isScreenOffTime(possibleStart) && !isScreenOffTime(possibleEnd)) {
            availableSlots.add("Start: " + possibleStart + ", End: " + possibleEnd);
        }

        if (isSlotAvailable) {
            return "Slot booked successfully.\nStart: " + bookingStart + "\nEnd: " + bookingEnd;
        } else {
            return "Requested slot is not available.\nAvailable slots:\n" + String.join("\n", availableSlots);
        }
    }

    private boolean isScreenOffTime(LocalDateTime time) {
        int hour = time.getHour();
        return (hour >= 22 || hour < 8); // 10 PM to 8 AM
    }
}
