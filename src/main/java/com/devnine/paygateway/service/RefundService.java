package com.devnine.paygateway.service;

import com.devnine.paygateway.entity.Payment;
import com.devnine.paygateway.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class RefundService {

    private final PaymentRepository paymentRepository;

    public RefundService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public void processRefund(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("해당 거래를 찾을 수 없습니다."));

        if (!"SUCCESS".equals(payment.getStatus())) {
            throw new IllegalStateException("환불할 수 없는 상태입니다.");
        }

        payment.setStatus("REFUNDED");
        paymentRepository.save(payment);
    }
}
