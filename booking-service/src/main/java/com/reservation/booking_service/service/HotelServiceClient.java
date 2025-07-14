package com.reservation.booking_service.service;


import com.reservation.booking_service.dto.*;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelServiceClient {
    @GetMapping("/api/hotels/{id}")
    HotelDTO getHotelById(@PathVariable Long id);
    
    @GetMapping("/api/hotels/available?destination={destination}")
    List<HotelDTO> getAvailableHotels(@PathVariable String destination);
}