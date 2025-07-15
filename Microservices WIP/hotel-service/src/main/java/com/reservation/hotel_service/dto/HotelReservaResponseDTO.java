package com.reservation.hotel_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class HotelReservaResponseDTO {
    private Long id;
    private Long idReserva;
    private String nombreHotel;
    private Long idCliente;
    private BigDecimal costo;
    private String estado;
}