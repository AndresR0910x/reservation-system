package com.reservation.booking_service.dto;

import lombok.Data;

@Data
public class CancelacionDTO {
    private Long idReserva;
    private Long idCliente;
}