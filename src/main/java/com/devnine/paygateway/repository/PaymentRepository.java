package com.devnine.paygateway.repository;

import com.devnine.paygateway.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByUserId(String userId);

    List<Payment> findByUserIdAndTimestampBetween(
            String userId, LocalDateTime startDate, LocalDateTime endDate);
}
