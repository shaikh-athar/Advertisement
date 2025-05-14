package com.gov.Advertisments.Service;

import com.gov.Advertisments.Model.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    List<Payment> getPaymentByStatus(String status);
    String savePayment(long bookingId);
}
