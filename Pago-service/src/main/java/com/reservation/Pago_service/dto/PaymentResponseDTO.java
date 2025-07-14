package com.reservation.Pago_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long id;
    private Long reservationId;
    private Long clientId;
    private Double amount;
    private String status;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String transactionId;
}