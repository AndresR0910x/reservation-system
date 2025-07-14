package com.reservation.aeroline_service.mapper;

import com.reservation.aeroline_service.dto.*;
import com.reservation.aeroline_service.model.*;
import org.springframework.stereotype.Component;

@Component
public class FlightMapper {
    public FlightDTO toDTO(Flight flight) {
        FlightDTO dto = new FlightDTO();
        dto.setId(flight.getId());
        dto.setAerolineaId(flight.getAerolineaId());
        dto.setOrigen(flight.getOrigen());
        dto.setDestino(flight.getDestino());
        dto.setFechaSalida(flight.getFechaSalida());
        dto.setFechaLlegada(flight.getFechaLlegada());
        dto.setPrecio(flight.getPrecio());
        dto.setDisponibilidad(flight.getDisponibilidad());
        dto.setAsientosDisponibles(flight.getAsientosDisponibles());
        return dto;
    }
    
    public Flight toEntity(FlightDTO dto) {
        Flight flight = new Flight();
        flight.setAerolineaId(dto.getAerolineaId());
        flight.setOrigen(dto.getOrigen());
        flight.setDestino(dto.getDestino());
        flight.setFechaSalida(dto.getFechaSalida());
        flight.setFechaLlegada(dto.getFechaLlegada());
        flight.setPrecio(dto.getPrecio());
        flight.setDisponibilidad(dto.getDisponibilidad());
        flight.setAsientosDisponibles(dto.getAsientosDisponibles());
        return flight;
    }
}