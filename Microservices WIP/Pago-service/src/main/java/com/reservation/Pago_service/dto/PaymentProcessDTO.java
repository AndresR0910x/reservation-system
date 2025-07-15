package com.reservation.Pago_service.dto;


import lombok.Data;

import jakarta.validation.constraints.*;




@Data
public class PaymentProcessDTO {
    @NotNull(message = "El ID del cliente es requerido")
    private Long clientId;
    
    @NotNull(message = "El monto es requerido")
    @Min(value = 1, message = "El monto debe ser mayor a 0")
    private Double amount;
    
    @NotNull(message = "El ID de reserva es requerido")
    private Long reservationId;
    
    private String paymentMethod; // WALLET, CREDIT_CARD, DEBIT_CARD
}