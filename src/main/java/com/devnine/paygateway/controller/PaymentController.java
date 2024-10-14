package com.devnine.paygateway.controller;

import com.devnine.paygateway.entity.Payment;
import com.devnine.paygateway.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment paymentRequest) {
        Payment payment = paymentService.createPayment(paymentRequest.getAmount());
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Payment> getPaymentStatus(@PathVariable String transactionId) {
        Optional<Payment> payment = paymentService.getPaymentByTransactionId(transactionId);
        return payment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
