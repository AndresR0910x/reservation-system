package com.reservation.client_service.dto;

import lombok.Data;

@Data
public class VerificacionSaldoResponseDTO {
    private boolean saldoSuficiente;
    private boolean dentroDeLimite;
    private String mensaje;
}