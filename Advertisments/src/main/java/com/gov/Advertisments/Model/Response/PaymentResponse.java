package com.gov.Advertisments.Model.Response;

import com.gov.Advertisments.Model.Enums.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentResponse {
    private String transactionId;
    private Double amount;
    private PaymentStatus status;
    private LocalDateTime paymentTime;

    public PaymentResponse(String transactionId, Double amount, PaymentStatus status, LocalDateTime paymentTime) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.status = status;
        this.paymentTime = paymentTime;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }
}
