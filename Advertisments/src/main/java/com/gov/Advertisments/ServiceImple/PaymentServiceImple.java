package com.gov.Advertisments.ServiceImple;

import com.gov.Advertisments.Model.Booking;
import com.gov.Advertisments.Model.Enums.BookingStatus;
import com.gov.Advertisments.Model.Enums.PaymentStatus;
import com.gov.Advertisments.Model.Payment;
import com.gov.Advertisments.Repository.BookingRepo;
import com.gov.Advertisments.Repository.PaymentRepo;
import com.gov.Advertisments.Service.PaymentService;
import com.gov.Advertisments.ServiceImple.OtherImple.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImple implements PaymentService {

    @Autowired
    private PaymentRepo paymentRepository;

    @Autowired
    private BookingRepo bookingRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }


    public List<Payment> getPaymentByStatus(String status){
        return paymentRepository.findByStatus(PaymentStatus.valueOf(status));
    }

    public String savePayment(long bookingId) {
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);

            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();
                Optional<Payment> existingPayment = paymentRepository.findByBooking(booking);

                if (existingPayment.isPresent()) {
                    Payment payment = existingPayment.get();
                    payment.setAmount(booking.getAmount());
                    payment.setPaymentTime(dateTime);
                    payment.setStatus(PaymentStatus.SUCCESS);
                    paymentRepository.save(payment);
                } else {
                    Payment payment = new Payment();
                    payment.setAmount(booking.getAmount());
                    payment.setTransactionId(IdGenerator.getId(8));
                    payment.setBooking(booking);
                    payment.setPaymentTime(dateTime);
                    payment.setStatus(PaymentStatus.SUCCESS);
                    paymentRepository.save(payment);
                }

                booking.setStatus(BookingStatus.CONFIRMED);
                bookingRepository.save(booking);

                return "Payment Successful";
            } else {
                return "Booking is Not Present";
            }
        } catch (Exception e) {
            return "Error in Payment : " + e.getMessage();
        }
    }
}
