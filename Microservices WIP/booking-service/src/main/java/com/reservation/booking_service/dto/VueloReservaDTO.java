package com.reservation.booking_service.dto;

import lombok.Data;

@Data
public class VueloReservaDTO {
    private Long idReserva;
    private String destino;
    private Long idCliente;
}