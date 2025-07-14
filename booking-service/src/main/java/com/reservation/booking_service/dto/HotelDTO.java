package com.reservation.booking_service.dto;


import lombok.Data;

@Data
public class HotelDTO {
    private Long id;
    private String nombre;
    private String ubicacion;
    private Integer cuartosDisponibles;
    private Double precioPorNoche;
}