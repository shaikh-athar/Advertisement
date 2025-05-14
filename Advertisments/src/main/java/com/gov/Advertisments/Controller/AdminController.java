package com.gov.Advertisments.Controller;

import com.gov.Advertisments.Model.*;
import com.gov.Advertisments.Model.Request.MaintainerRequest;
import com.gov.Advertisments.Model.Request.MaintenanceRequest;
import com.gov.Advertisments.Model.Request.ScreenRequest;
import com.gov.Advertisments.Model.Request.UserRequest;
import com.gov.Advertisments.Service.*;
import com.gov.Advertisments.ServiceImple.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    ScreenService screenService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    BannerService bannerService;
    @Autowired
    BookingService bookingService;
    @Autowired
    MaintenanceService maintenanceService;
    @Autowired
    MaintainerService maintainerService;

    @GetMapping
    public List<User> getAllUsersDetails() {
        return userService.getAllUsers();
    }
    @GetMapping("/role")
    public List<User> getUserByRoles(@RequestParam("roles") String roles){
        return userService.getUserByRoles(roles);
    }

    @PostMapping
    public String createOrUpdateUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.addUser(userRequest);
    }

    @PatchMapping("/update/{email}")
    public String updateUser(@PathVariable String email, @RequestBody UserRequest userRequest) {
        return userService.updateUser(email, userRequest);
    }

    @DeleteMapping("/remove-user/{email}")
    public String deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

    @PostMapping("/screens")
    public String createScreen(@RequestBody @Valid ScreenRequest screenRequest) {
        return screenService.saveScreen(screenRequest);
    }
    @DeleteMapping("/screens/{id}")
    public String deleteScreen(@PathVariable Long id) {
        return screenService.deleteScreen(id);
    }

    @GetMapping("/payments")
    public List<Payment> getPaymentByStatus(@RequestParam("status") String status){
        return paymentService.getPaymentByStatus(status);
    }

    @GetMapping("/bookings")
    public List<Booking> getAllBookingDetails() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/bookings/{status}")
    public List<Booking> getBookingByStatus(@PathVariable("status") String status){
        return bookingService.getBookingByStatus(status);
    }

    @GetMapping("/banners")
    public List<Banner> getAllBannerDetails() {
        return bannerService.getAllBanners();
    }

    @PostMapping("/maintenance")
    public String addMaintenance(@RequestBody MaintenanceRequest maintenanceRequest) {
        return maintenanceService.addMaintenance(maintenanceRequest);
    }

    @PatchMapping("/maintenance/{complaintId}")
    public String updateMaintenance(@PathVariable String complaintId, @RequestBody MaintenanceRequest maintenanceRequest) {
        return maintenanceService.updateMaintenance(complaintId, maintenanceRequest);
    }

    @PostMapping("/maintainer")
    public String addMaintainer(@RequestBody @Valid MaintainerRequest maintainerRequest) {
        return maintainerService.addMaintainer(maintainerRequest);
    }

    @GetMapping("/maintainer")
    public List<Maintainer> getAllMaintainers() {
        return maintainerService.getAllMaintainers();
    }

    @GetMapping("/maintainer/{maintainerId}")
    public Maintainer getMaintainerById(@PathVariable String maintainerId) {
        return maintainerService.getMaintainerById(maintainerId);
    }

    @PostMapping("/maintainer/fixed/{id}")
    public String ScreenRepairedRequest(@PathVariable long id){
        return maintenanceService.screenRepaired(id);
    }

    @PatchMapping("/maintainer/{maintainerId}")
    public String updateMaintainer(@PathVariable String maintainerId, @RequestBody @Valid MaintainerRequest maintainerRequest) {
        return maintainerService.updateMaintainer(maintainerId, maintainerRequest);
    }

    @DeleteMapping("/maintainer/{maintainerId}")
    public String deleteMaintainer(@PathVariable String maintainerId) {
        return maintainerService.deleteMaintainer(maintainerId);
    }
}
