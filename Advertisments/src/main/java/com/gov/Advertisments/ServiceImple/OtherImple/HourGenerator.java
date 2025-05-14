package com.gov.Advertisments.ServiceImple.OtherImple;

import java.time.Duration;
import java.time.LocalDateTime;

public class HourGenerator {
        public static int getHours(LocalDateTime startTime, LocalDateTime endTime){
            Duration duration = Duration.between(startTime, endTime);
            long totalMinutes = duration.toMinutes();
            return (int) Math.ceil(totalMinutes / 60.0);
        }
}
