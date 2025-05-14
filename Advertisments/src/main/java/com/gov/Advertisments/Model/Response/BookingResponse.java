package com.gov.Advertisments.Model.Response;

import com.gov.Advertisments.Model.Enums.BookingStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.List;


public class BookingResponse {
        private String bookingId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private BookingStatus status;
        private UserResponse advertiser;
        private List<ScreenResponse> screens;
        private PaymentResponse payment;

    public BookingResponse(String bookingId, LocalDateTime startTime, LocalDateTime endTime, BookingStatus status, UserResponse advertiser, List<ScreenResponse> screens, PaymentResponse payment) {
        this.bookingId = bookingId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.advertiser = advertiser;
        this.screens = screens;
        this.payment = payment;
    }

    public String getBookingId() {
            return bookingId;
        }

        public void setBookingId(String bookingId) {
            this.bookingId = bookingId;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public BookingStatus getStatus() {
            return status;
        }

        public void setStatus(BookingStatus status) {
            this.status = status;
        }

        public UserResponse getAdvertiser() {
            return advertiser;
        }

        public void setAdvertiser(UserResponse advertiser) {
            this.advertiser = advertiser;
        }

        public List<ScreenResponse> getScreens() {
            return screens;
        }

        public void setScreens(List<ScreenResponse> screens) {
            this.screens = screens;
        }

        public PaymentResponse getPayment() {
            return payment;
        }

        public void setPayment(PaymentResponse payment) {
            this.payment = payment;
        }
    }
