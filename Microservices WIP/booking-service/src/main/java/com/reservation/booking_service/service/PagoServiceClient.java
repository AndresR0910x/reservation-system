package com.reservation.booking_service.service;

import com.reservation.booking_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "pago-service")
public interface PagoServiceClient {
    @PostMapping("/api/pago/procesar")
    PagoResponseDTO procesarPago(@RequestBody PagoDTO pagoDTO);
}