package com.reservation.hotel_service.dto;

import lombok.Data;

@Data
public class CancelacionDTO {
    private Long idReserva;
    private Long idCliente;
}