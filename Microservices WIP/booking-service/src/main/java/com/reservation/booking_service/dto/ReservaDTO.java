package com.reservation.booking_service.dto;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReservaDTO {
    private Long idCliente;
    private String destino;
    private String nombreHotel;
    private BigDecimal montoTotal;
}