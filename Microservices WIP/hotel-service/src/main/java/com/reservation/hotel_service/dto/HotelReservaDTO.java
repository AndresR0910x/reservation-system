package com.reservation.hotel_service.dto;

import lombok.Data;

@Data
public class HotelReservaDTO {
    private Long idReserva;
    private String nombreHotel;
    private Long idCliente;
}