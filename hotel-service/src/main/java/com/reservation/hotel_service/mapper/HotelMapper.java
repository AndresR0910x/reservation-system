package com.reservation.hotel_service.mapper;

import com.reservation.hotel_service.dto.*;
import com.reservation.hotel_service.model.*;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {
    public HotelDTO toDTO(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setId(hotel.getId());
        dto.setNombre(hotel.getNombre());
        dto.setUbicacion(hotel.getUbicacion());
        dto.setCuartosDisponibles(hotel.getCuartosDisponibles());
        dto.setPrecioPorNoche(hotel.getPrecioPorNoche());
        dto.setDisponibilidad(hotel.getDisponibilidad());
        return dto;
    }
    
    public Hotel toEntity(HotelDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setNombre(dto.getNombre());
        hotel.setUbicacion(dto.getUbicacion());
        hotel.setCuartosDisponibles(dto.getCuartosDisponibles());
        hotel.setPrecioPorNoche(dto.getPrecioPorNoche());
        hotel.setDisponibilidad(dto.getDisponibilidad());
        return hotel;
    }
}