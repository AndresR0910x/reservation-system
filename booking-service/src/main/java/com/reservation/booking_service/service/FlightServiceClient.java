package com.reservation.booking_service.service;

import com.reservation.booking_service.dto.*;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "AEROLINE-SERVICE")
public interface FlightServiceClient {
    @GetMapping("/api/flights/{id}")
    FlightDTO getFlightById(@PathVariable Long id);
    
    @GetMapping("/api/flights/available?origin={origin}&destination={destination}")
    List<FlightDTO> getAvailableFlights(@PathVariable String origin, @PathVariable String destination);
}