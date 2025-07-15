package com.reservation.booking_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SaldoDTO {
    private Long idCliente;
    private BigDecimal monto;
}