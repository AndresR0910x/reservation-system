package com.reservation.aeroline_service.service;

import com.reservation.aeroline_service.dto.*;
import com.reservation.aeroline_service.model.*;
import com.reservation.aeroline_service.exception.*;
import com.reservation.aeroline_service.mapper.*;
import com.reservation.aeroline_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final AirlineService airlineService;
    
    public FlightDTO createFlight(FlightDTO flightDTO) {
        // Verificar que la aerolínea existe
        airlineService.getAirlineById(flightDTO.getAerolineaId());
        
        Flight flight = flightMapper.toEntity(flightDTO);
        Flight savedFlight = flightRepository.save(flight);
        return flightMapper.toDTO(savedFlight);
    }
    
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        return flightMapper.toDTO(flight);
    }
    
    public List<FlightDTO> getAvailableFlights(String origen, String destino) {
        List<Flight> flights = flightRepository
                .findByOrigenAndDestinoAndFechaSalidaAfterAndDisponibilidadTrue(
                    origen, destino, LocalDateTime.now());
        
        return flights.stream()
                .map(flightMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public FlightDTO updateFlight(Long id, FlightDTO flightDTO) {
        Flight existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
        
        // Verificar que la aerolínea existe
        airlineService.getAirlineById(flightDTO.getAerolineaId());
        
        existingFlight.setAerolineaId(flightDTO.getAerolineaId());
        existingFlight.setOrigen(flightDTO.getOrigen());
        existingFlight.setDestino(flightDTO.getDestino());
        existingFlight.setFechaSalida(flightDTO.getFechaSalida());
        existingFlight.setFechaLlegada(flightDTO.getFechaLlegada());
        existingFlight.setPrecio(flightDTO.getPrecio());
        existingFlight.setDisponibilidad(flightDTO.getDisponibilidad());
        existingFlight.setAsientosDisponibles(flightDTO.getAsientosDisponibles());
        
        Flight updatedFlight = flightRepository.save(existingFlight);
        return flightMapper.toDTO(updatedFlight);
    }
    
    public void deleteFlight(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new ResourceNotFoundException("Flight not found with id: " + id);
        }
        flightRepository.deleteById(id);
    }
    
    public void decreaseAvailableSeats(Long flightId, int seats) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + flightId));
        
        if (flight.getAsientosDisponibles() < seats) {
            throw new IllegalArgumentException("Not enough available seats");
        }
        
        flight.setAsientosDisponibles(flight.getAsientosDisponibles() - seats);
        if (flight.getAsientosDisponibles() == 0) {
            flight.setDisponibilidad(false);
        }
        
        flightRepository.save(flight);
    }
}