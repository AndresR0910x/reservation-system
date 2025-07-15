package com.reservation.booking_service.dto;

import com.reservation.booking_service.model.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReservaResponseDTO {
    private Long id;
    private Long idCliente;
    private String destino;
    private String nombreHotel;
    private BigDecimal montoTotal;
    private Reserva.EstadoReserva estado;
    private Long idReservaHotel;
    private Long idReservaVuelo;
}