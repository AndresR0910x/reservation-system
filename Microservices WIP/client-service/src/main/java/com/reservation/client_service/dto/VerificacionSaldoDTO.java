package com.reservation.client_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class VerificacionSaldoDTO {
    private Long idCliente;
    private BigDecimal montoAReservar;
}