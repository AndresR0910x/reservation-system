package com.reservation.Pago_service.dto;


import lombok.Data;

@Data
public class PaymentRefundDTO {
    private String reason;
    private Double refundAmount;
}