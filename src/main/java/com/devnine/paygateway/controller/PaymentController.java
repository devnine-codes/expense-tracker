package com.devnine.paygateway.controller;

import com.devnine.paygateway.entity.Payment;
import com.devnine.paygateway.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam String userId,
            @RequestParam Double amount) {
        Payment payment = paymentService.createPayment(userId, amount);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Payment> getPaymentStatus(@PathVariable String transactionId) {
        Optional<Payment> payment = paymentService.getPaymentByTransactionId(transactionId);
        return payment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/history")
    public ResponseEntity<List<Payment>> getPaymentHistory(
            @RequestParam String userId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        List<Payment> paymentHistory = (startDate != null && endDate != null)
                ? paymentService.getPaymentHistory(userId, startDate, endDate)
                : paymentService.getPaymentHistory(userId);
        return ResponseEntity.ok(paymentHistory);
    }
}
