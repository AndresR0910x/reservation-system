package com.reservation.booking_service.service;


import com.reservation.booking_service.model.*;
import com.reservation.booking_service.repository.ReservationRepository;
import com.reservation.booking_service.dto.FlightDTO;
import com.reservation.booking_service.dto.ReservationRequestDTO;
import com.reservation.booking_service.dto.ReservationResponseDTO;
import com.reservation.booking_service.dto.HotelDTO;
import com.reservation.booking_service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final FlightServiceClient flightServiceClient;
    private final HotelServiceClient hotelServiceClient;
    
    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO) {
        // Validar vuelo
        FlightDTO flight = flightServiceClient.getFlightById(requestDTO.getFlightId());
        if (flight == null || !flight.getDisponibilidad()) {
            throw new ResourceNotFoundException("Flight not available");
        }
        
        // Validar hotel
        HotelDTO hotel = hotelServiceClient.getHotelById(requestDTO.getHotelId());
        if (hotel == null || hotel.getCuartosDisponibles() <= 0) {
            throw new ResourceNotFoundException("Hotel not available");
        }
        
        // Calcular total
        Double totalPrice = (flight.getPrecio() * requestDTO.getSeatsNumber()) + hotel.getPrecioPorNoche();
        
        // Crear reserva
        Reservation reservation = new Reservation();
        reservation.setClientId(requestDTO.getClientId());
        reservation.setOrigin(requestDTO.getOrigin());
        reservation.setDestination(requestDTO.getDestination());
        reservation.setFlightId(requestDTO.getFlightId());
        reservation.setHotelId(requestDTO.getHotelId());
        reservation.setSeatsNumber(requestDTO.getSeatsNumber());
        reservation.setTotalPrice(totalPrice);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus("CONFIRMED");
        
        Reservation savedReservation = reservationRepository.save(reservation);
        
        return mapToResponseDTO(savedReservation);
    }
    
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    public ReservationResponseDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return mapToResponseDTO(reservation);
    }
    
    private ReservationResponseDTO mapToResponseDTO(Reservation reservation) {
        ReservationResponseDTO responseDTO = new ReservationResponseDTO();
        responseDTO.setId(reservation.getId());
        responseDTO.setClientId(reservation.getClientId());
        responseDTO.setOrigin(reservation.getOrigin());
        responseDTO.setDestination(reservation.getDestination());
        responseDTO.setFlightId(reservation.getFlightId());
        responseDTO.setHotelId(reservation.getHotelId());
        responseDTO.setSeatsNumber(reservation.getSeatsNumber());
        responseDTO.setTotalPrice(reservation.getTotalPrice());
        responseDTO.setReservationDate(reservation.getReservationDate());
        responseDTO.setStatus(reservation.getStatus());
        return responseDTO;
    }
}