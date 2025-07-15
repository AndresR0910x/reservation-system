package com.reservation.booking_service.service;

import com.reservation.booking_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "vuelo-service")
public interface VueloServiceClient {
    @PostMapping("/api/vuelo/reservar")
    VueloReservaResponseDTO reservarVuelo(@RequestBody VueloReservaDTO reservaDTO);

    @PostMapping("/api/vuelo/cancelar")
    boolean cancelarReservaVuelo(@RequestBody CancelacionDTO cancelacionDTO);
}