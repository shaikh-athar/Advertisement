package com.gov.Advertisments.Service;

import com.gov.Advertisments.Model.Booking;
import com.gov.Advertisments.Model.Request.BookingRequest;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    List<Booking> getAllBookings();
    Optional<Booking> getBookingById(String bookingId);
    List<Booking> getBookingByStatus(String status);
    String addBooking(BookingRequest bookingRequest);
    String updateBooking(BookingRequest bookingRequest, String bookingId);
    String cancelBooking(String bookingId);
}
