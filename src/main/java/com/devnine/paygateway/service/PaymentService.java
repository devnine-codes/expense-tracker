package com.devnine.paygateway.service;

import com.devnine.paygateway.entity.Payment;
import com.devnine.paygateway.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment createPayment(Double amount) {
        Payment payment = new Payment();
        payment.setTransactionId("TXN-" + System.currentTimeMillis());
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        payment.setTimestamp(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public Optional<Payment> getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }
}
