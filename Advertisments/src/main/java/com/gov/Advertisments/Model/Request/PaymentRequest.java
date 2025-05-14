package com.gov.Advertisments.Model.Request;

import com.gov.Advertisments.Model.Enums.PaymentStatus;
import jakarta.validation.constraints.*;

public class PaymentRequest {

    @NotNull(message = "Booking ID is required")
    @Positive(message = "Booking ID must be greater than zero")
    private Long bookingId;


    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
}
