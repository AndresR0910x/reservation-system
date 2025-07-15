package com.reservation.booking_service.service;

import com.reservation.booking_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "hotel-service")
public interface HotelServiceClient {
    @PostMapping("/api/hotel/reservar")
    HotelReservaResponseDTO reservarHotel(@RequestBody HotelReservaDTO reservaDTO);

    @PostMapping("/api/hotel/cancelar")
    boolean cancelarReservaHotel(@RequestBody CancelacionDTO cancelacionDTO);
}