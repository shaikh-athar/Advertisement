package com.gov.Advertisments.Controller;

import com.gov.Advertisments.Model.Booking;
import com.gov.Advertisments.Model.Request.BannerRequest;
import com.gov.Advertisments.Model.Request.BookingRequest;
import com.gov.Advertisments.Model.Response.Exception.BookingNotFoundException;
import com.gov.Advertisments.Model.Response.ScreenResponse;
import com.gov.Advertisments.Model.Screen;
import com.gov.Advertisments.Service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ScreenService screenService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    BookingService bookingService;
    @Autowired
    BannerService bannerService;

    @GetMapping("/screens")
    public List<Screen> getAllScreens() {
        return screenService.getAllScreens();
    }

    @GetMapping("/get-screens")
    public List<Screen> getScreenByLocationAndActive(@RequestParam("location")String location,@RequestParam("active")Boolean active){
        return screenService.getScreenByLocationAndActive(location,active);
    }
    @GetMapping("/screens/rating/{rating}")
    public List<ScreenResponse> getScreenByRating(@PathVariable("rating") int rating ){
        return screenService.getLocationByRating(rating);
    }

    @PostMapping("/payment/{bookingId}")
    public String makePayment(@PathVariable("bookingId") long bookingId) {
        return paymentService.savePayment(bookingId);
    }

    @GetMapping("/booking/{bookingId}")
    public Optional<Booking> getBookingById(@PathVariable String bookingId) {
        Booking booking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found for ID: " + bookingId));

        return Optional.ofNullable(booking);
    }

    @PostMapping("/booking")
    public String createOrUpdateBooking(@RequestBody @Valid BookingRequest bookingRequest) {
        return bookingService.addBooking(bookingRequest);
    }

    @PatchMapping("/booking/{bookingId}")
    public String updateBooking(@PathVariable String bookingId, @RequestBody BookingRequest bookingRequest) {
        return bookingService.updateBooking(bookingRequest ,bookingId);
    }

    @DeleteMapping("/booking/{bookingId}")
    public String cancelBooking(@PathVariable String bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @PostMapping("/banners")
    public String addBanner(@RequestBody @Valid BannerRequest bannerRequest) {
        return bannerService.saveBanner(bannerRequest);
    }
    @DeleteMapping("banners/{id}")
    public String deleteUser(@PathVariable Long id) {
        return bannerService.deleteScreen(id);
    }
}
