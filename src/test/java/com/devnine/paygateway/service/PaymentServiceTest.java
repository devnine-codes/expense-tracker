package com.devnine.paygateway.service;

import com.devnine.paygateway.entity.Payment;
import com.devnine.paygateway.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    private final double DAILY_LIMIT = 50000.0;  // 하루 결제 한도

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPayment_ShouldThrowException_WhenDailyLimitExceeded() {
        // Given: 이미 오늘 40,000원 결제된 상황
        String userId = "devnine";
        List<Payment> todayPayments = List.of(
                new Payment(null, "TXN-111", userId, 40000.0, "SUCCESS", LocalDateTime.now())
        );

        when(paymentRepository.findByUserIdAndTimestampBetween(
                eq(userId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(todayPayments);

        // When & Then: 사용자가 추가로 20,000원을 결제하면 예외 발생
        assertThatThrownBy(() -> paymentService.createPayment(userId, 20000.0))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("하루 결제 한도를 초과했습니다.");
    }

    @Test
    void createPayment_ShouldSucceed_WhenDailyLimitNotExceeded() {
        // Given: 오늘 결제가 아직 없을 때
        String userId = "devnine";
        when(paymentRepository.findByUserIdAndTimestampBetween(
                eq(userId), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of());

        // When: 사용자가 20,000원을 결제
        Payment payment = new Payment(null, "TXN-123", userId, 20000.0, "PENDING", LocalDateTime.now());
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        // Then: 결제가 정상적으로 생성됨
        Payment result = paymentService.createPayment(userId, 20000.0);
        assertThat(result.getAmount()).isEqualTo(20000.0);
        assertThat(result.getStatus()).isEqualTo("PENDING");
    }
}
