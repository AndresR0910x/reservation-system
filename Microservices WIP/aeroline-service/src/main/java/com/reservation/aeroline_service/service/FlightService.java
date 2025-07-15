package com.reservation.aeroline_service.service;

import com.reservation.aeroline_service.dto.*;
import com.reservation.aeroline_service.model.*;
import com.reservation.aeroline_service.exception.*;
import com.reservation.aeroline_service.mapper.*;
import com.reservation.aeroline_service.repository.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final AirlineService airlineService;
    private final ClientServiceClient clientServiceClient;
    
    public FlightDTO createFlight(FlightDTO flightDTO) {
        // Verificar que la aerolínea existe
        airlineService.getAirlineById(flightDTO.getAerolineaId());
        
        Flight flight = flightMapper.toEntity(flightDTO);
        flight.setStatus("AVAILABLE");
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

    @Transactional
public FlightBookingResponseDTO bookFlight(FlightBookingRequestDTO bookingRequest) {
    // Verificar que el cliente existe primero
    ClientDTO client = clientServiceClient.getClientById(bookingRequest.getClientId());
    if (client == null) {
        throw new ResourceNotFoundException("Cliente no encontrado con ID: " + bookingRequest.getClientId());
    }

    // Buscar vuelos disponibles al destino
    List<Flight> availableFlights = flightRepository
            .findByDestinoAndStatusAndDisponibilidadTrue(
                bookingRequest.getDestino(), "AVAILABLE");

    if (availableFlights.isEmpty()) {
        throw new ResourceNotFoundException("No hay vuelos disponibles al destino especificado");
    }

    // Seleccionar el primer vuelo disponible (podría mejorarse la lógica de selección)
    Flight flight = availableFlights.get(0);

    // Reservar el vuelo
    flight.setStatus("RESERVED");
    flight.setClientId(bookingRequest.getClientId()); // Almacenar ID del cliente
    flight.setReservationDate(LocalDateTime.now());
    flightRepository.save(flight);

    // Generar respuesta
    FlightBookingResponseDTO response = new FlightBookingResponseDTO();
    response.setFlightId(flight.getId());
    response.setReservationId("FLT-" + UUID.randomUUID().toString().substring(0, 8));
    response.setStatus(flight.getStatus());
    response.setReservationDate(flight.getReservationDate());
    response.setMessage("Vuelo reservado exitosamente. Complete el pago para confirmar.");

    return response;
}


    @Transactional
public FlightDTO updateFlightStatus(Long id, FlightStatusUpdateDTO statusUpdate) {
    Flight flight = flightRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Vuelo no encontrado con ID: " + id));
    
    String newStatus = statusUpdate.getStatus();

    if ("CONFIRMED".equals(newStatus)) {
        if (!"RESERVED".equals(flight.getStatus())) {
            throw new InvalidOperationException("Solo vuelos RESERVED pueden ser CONFIRMED");
        }

        if (flight.getClientId() == null) {
            throw new InvalidOperationException("No se puede confirmar reserva sin cliente asociado");
        }

        // Obtener saldo del cliente por su ID
        ClientBalanceDTO clientBalance = clientServiceClient.getClientBalance(flight.getClientId());

        if (clientBalance.getBalance() == null || clientBalance.getBalance().compareTo(flight.getPrecio()) < 0) {
            // Cancelar automáticamente el vuelo
            flight.setStatus("CANCELLED");
            flight.setCancellationReason("Saldo insuficiente para confirmar reserva");
            flight.setDisponibilidad(true);
            flight.setAsientosDisponibles(flight.getAsientosDisponibles() + 1);
            flightRepository.save(flight);

            throw new InsufficientBalanceException(
                String.format("El cliente ID %d no tiene saldo suficiente. Balance actual: %.2f, Se requiere: %.2f",
                    flight.getClientId(),
                    clientBalance.getBalance(),
                    flight.getPrecio())
            );
        }

        // Aquí iría la lógica para procesar el pago (si aplica)
    }

    if ("CANCELLED".equals(newStatus)) {
        if ("RESERVED".equals(flight.getStatus()) || "CONFIRMED".equals(flight.getStatus())) {
            flight.setDisponibilidad(true);
            flight.setAsientosDisponibles(flight.getAsientosDisponibles() + 1);
        }
        flight.setCancellationReason(statusUpdate.getReason());
    }

    flight.setStatus(newStatus);
    Flight updatedFlight = flightRepository.save(flight);

    return flightMapper.toDTO(updatedFlight);
}

}