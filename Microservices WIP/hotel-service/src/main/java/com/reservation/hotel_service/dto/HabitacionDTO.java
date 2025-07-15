package com.reservation.hotel_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class HabitacionDTO {
    private Long hotelId;
    private String tipo;
    private Integer capacidad;
    private BigDecimal precioPorNoche;
}