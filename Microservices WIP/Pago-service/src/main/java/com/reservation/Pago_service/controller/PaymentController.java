package com.reservation.Pago_service.controller;

import com.reservation.Pago_service.dto.*;
import com.reservation.Pago_service.service.PaymentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @RequestBody PaymentProcessDTO paymentProcessDTO) {
        PaymentResponseDTO response = paymentService.processPayment(paymentProcessDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentResponseDTO> processRefund(
            @PathVariable Long id,
            @RequestParam(required = false) String reason) {
        PaymentResponseDTO response = paymentService.processRefund(id, reason);
        return ResponseEntity.ok(response);
    }
}