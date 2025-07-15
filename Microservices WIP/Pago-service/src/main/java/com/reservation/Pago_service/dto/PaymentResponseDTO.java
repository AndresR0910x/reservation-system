package com.reservation.Pago_service.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long id;
    private Long clientId;
    private Double amount;
    private String status; // COMPLETED, REFUNDED, FAILED
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String transactionId;
    private Long reservationId;
    private LocalDateTime refundDate;
    private String refundReason;
}