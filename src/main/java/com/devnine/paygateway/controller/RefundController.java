package com.devnine.paygateway.controller;

import com.devnine.paygateway.service.RefundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refunds")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping("/{transactionId}")
    public ResponseEntity<String> requestRefund(@PathVariable String transactionId) {
        refundService.processRefund(transactionId);
        return ResponseEntity.ok("환불 요청이 접수되었습니다.");
    }
}
