package com.reservation.Pago_service.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long reservationId;
    private Long clientId;
    private Double amount;
    private String paymentMethod;
}