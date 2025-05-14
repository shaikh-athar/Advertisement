package com.gov.Advertisments.Repository;

import com.gov.Advertisments.Model.Booking;
import com.gov.Advertisments.Model.Enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepo extends JpaRepository<Booking,Long> {
    Optional<Booking> findByBookingId(String bookingId);

    List<Booking> findByScreenId(long screenId);

    List<Booking> findByStatus(BookingStatus status);

    @Query(value = "update Booking b set b.status = CANCELLED where b.bookingId = :bookingId")
//    InvalidDataAccessApiUsage
    @Modifying
    void cancelBooking(@Param("bookingId") String bookingId);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE (b.startTime < :endTime AND b.endTime > :startTime)")
    boolean findBookingSlot(@Param("startTime") LocalDateTime startTime,
                            @Param("endTime") LocalDateTime endTime);

    @Query(value = "SELECT COUNT(b) > 0 FROM Booking b WHERE b.bookingId != :bookingId AND (b.startTime < :endTime AND b.endTime > :startTime)")
    boolean SlotAvailableForUpdate(@Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime,
                                     @Param("bookingId") String bookingId);

    List<Booking> findAllByOrderByStartTimeAsc();
}
