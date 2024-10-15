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

    // 1. 결제 생성
    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @RequestParam String userId,
            @RequestParam Double amount) {
        Payment payment = paymentService.createPayment(userId, amount);
        return ResponseEntity.ok(payment);
    }

    // 2. 거래 ID로 결제 상태 조회
    @GetMapping("/{transactionId}")
    public ResponseEntity<Payment> getPaymentStatus(@PathVariable String transactionId) {
        Optional<Payment> payment = paymentService.getPaymentByTransactionId(transactionId);
        return payment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 3. 결제 상태 업데이트 (PENDING -> SUCCESS 등)
    @PatchMapping("/{transactionId}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable String transactionId,
            @RequestParam String newStatus) {
        Payment updatedPayment = paymentService.updatePaymentStatus(transactionId, newStatus);
        return ResponseEntity.ok(updatedPayment);
    }

    // 4. 환불 요청
    @PostMapping("/{transactionId}/refund")
    public ResponseEntity<Payment> requestRefund(@PathVariable String transactionId) {
        Payment refundedPayment = paymentService.processRefund(transactionId);
        return ResponseEntity.ok(refundedPayment);
    }
}
