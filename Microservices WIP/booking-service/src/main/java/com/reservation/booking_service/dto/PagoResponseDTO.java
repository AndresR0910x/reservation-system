package com.reservation.booking_service.dto;

import lombok.Data;

@Data
public class PagoResponseDTO {
    private boolean exitoso;
    private String mensaje;
}