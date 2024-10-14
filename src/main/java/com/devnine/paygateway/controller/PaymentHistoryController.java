package com.devnine.paygateway.controller;

import com.devnine.paygateway.entity.Payment;
import com.devnine.paygateway.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/payments/history")
public class PaymentHistoryController {

    private final PaymentService paymentService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public PaymentHistoryController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPaymentHistory(
            @RequestParam String userId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        // 날짜 파라미터를 LocalDateTime으로 변환
        LocalDateTime start = parseDate(startDate);
        LocalDateTime end = parseDate(endDate);

        List<Payment> paymentHistory = paymentService.getPaymentHistory(userId, start, end);
        return ResponseEntity.ok(paymentHistory);
    }

    // String -> LocalDateTime 변환 (유효하지 않은 형식 처리)
    private LocalDateTime parseDate(String date) {
        try {
            return (date != null) ? LocalDateTime.parse(date, FORMATTER) : null;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("유효하지 않은 날짜 형식입니다: " + date);
        }
    }
}