package com.devnine.paygateway.service;

import com.devnine.paygateway.entity.Payment;
import com.devnine.paygateway.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 1. 결제 생성
    public Payment createPayment(String userId, Double amount) {
        Payment payment = new Payment();
        payment.setTransactionId("TXN-" + System.currentTimeMillis());
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setStatus("PENDING");
        payment.setTimestamp(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    // 2. 거래 ID로 결제 조회
    public Optional<Payment> getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findByTransactionId(transactionId);
    }

    // 3. 사용자 ID로 결제 이력 조회
    public List<Payment> getPaymentHistory(String userId) {
        return paymentRepository.findByUserId(userId);
    }

    // 4. 기간별 결제 이력 조회
    public List<Payment> getPaymentHistory(String userId, LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepository.findByUserIdAndTimestampBetween(userId, startDate, endDate);
    }

    // 5. 결제 상태 업데이트 (PENDING -> SUCCESS 등)
    @Transactional
    public Payment updatePaymentStatus(String transactionId, String newStatus) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 거래를 찾을 수 없습니다."));

        payment.setStatus(newStatus);
        return paymentRepository.save(payment);
    }

    // 6. 환불 처리
    @Transactional
    public Payment processRefund(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 거래를 찾을 수 없습니다."));

        if (!"SUCCESS".equals(payment.getStatus())) {
            throw new IllegalStateException("환불할 수 없는 상태입니다.");
        }

        payment.setStatus("REFUNDED");
        return paymentRepository.save(payment);
    }
}
