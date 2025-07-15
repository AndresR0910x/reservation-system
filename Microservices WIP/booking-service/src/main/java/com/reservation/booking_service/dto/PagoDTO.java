package com.reservation.booking_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoDTO {
    private Long idReserva;
    private Long idCliente;
    private BigDecimal monto;
}