package com.reservation.aeroline_service.controller;

import com.reservation.aeroline_service.dto.*;
import com.reservation.aeroline_service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    
    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@RequestBody FlightDTO flightDTO) {
        FlightDTO createdFlight = flightService.createFlight(flightDTO);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id) {
        FlightDTO flight = flightService.getFlightById(id);
        return ResponseEntity.ok(flight);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<FlightDTO>> getAvailableFlights(
            @RequestParam String origin,
            @RequestParam String destination) {
        List<FlightDTO> flights = flightService.getAvailableFlights(origin, destination);
        return ResponseEntity.ok(flights);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO) {
        FlightDTO updatedFlight = flightService.updateFlight(id, flightDTO);
        return ResponseEntity.ok(updatedFlight);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/book")
    public ResponseEntity<FlightBookingResponseDTO> bookFlight(
            @RequestBody FlightBookingRequestDTO bookingRequest) {
        FlightBookingResponseDTO response = flightService.bookFlight(bookingRequest);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightDTO> updateFlightStatus(
            @PathVariable("id") Long flightId,
            @RequestBody FlightStatusUpdateDTO statusUpdate) {
        FlightDTO updatedFlight = flightService.updateFlightStatus(flightId, statusUpdate);
        return ResponseEntity.ok(updatedFlight);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<FlightDTO> cancelFlightReservation(
            @PathVariable("id") Long flightId,
            @RequestParam(required = false) String reason) {
        FlightStatusUpdateDTO cancellation = new FlightStatusUpdateDTO();
        cancellation.setStatus("CANCELLED");
        cancellation.setReason(reason);
        
        FlightDTO updatedFlight = flightService.updateFlightStatus(flightId, cancellation);
        return ResponseEntity.ok(updatedFlight);
    }
}