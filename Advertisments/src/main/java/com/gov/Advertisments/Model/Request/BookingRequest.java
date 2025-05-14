package com.gov.Advertisments.Model.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gov.Advertisments.Model.Enums.BookingStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class BookingRequest {

    @NotNull(message = "Start time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent(message = "Booking Start time must be Present Or Future")
    // DateTimeParseException
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "Booking End Time be Future")
    private LocalDateTime endTime;

    @NotBlank(message = "Advertiser name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Advertiser name must contain only letters and spaces")
    private String advertiserName;

    @NotNull(message = "Screen ID is required")
    private long screenIds;


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

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName;
    }

    public long getScreenIds() {
        return screenIds;
    }

    public void setScreenIds(long screenIds) {
        this.screenIds = screenIds;
    }
}
