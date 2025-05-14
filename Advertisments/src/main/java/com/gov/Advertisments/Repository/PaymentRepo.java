package com.gov.Advertisments.Repository;

import com.gov.Advertisments.Model.Booking;
import com.gov.Advertisments.Model.Enums.PaymentStatus;
import com.gov.Advertisments.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByStatus(PaymentStatus status);

    Optional<Payment> findByBooking(Booking booking);
}
