package com.reservation.aeroline_service.mapper;

import com.reservation.aeroline_service.dto.*;
import com.reservation.aeroline_service.model.*;
import org.springframework.stereotype.Component;

@Component
public class AirlineMapper {
    public AirlineDTO toDTO(Airline airline) {
        AirlineDTO dto = new AirlineDTO();
        dto.setId(airline.getId());
        dto.setNombre(airline.getNombre());
        dto.setCodigo(airline.getCodigo());
        return dto;
    }
    
    public Airline toEntity(AirlineDTO dto) {
        Airline airline = new Airline();
        airline.setNombre(dto.getNombre());
        airline.setCodigo(dto.getCodigo());
        return airline;
    }
}