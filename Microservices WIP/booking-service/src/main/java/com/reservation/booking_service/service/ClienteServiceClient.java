package com.reservation.booking_service.service;

import com.reservation.booking_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cliente-service", url = "${reserva.cliente-service-url}")
public interface ClienteServiceClient {
    @PostMapping("/api/cliente/verificar-saldo")
    VerificacionSaldoResponseDTO verificarSaldo(@RequestBody VerificacionSaldoDTO verificacionDTO);
    
    @PostMapping("/api/cliente/reducir-saldo")
    Boolean reducirSaldo(@RequestBody SaldoDTO saldoDTO);
    
    @PostMapping("/api/cliente/aumentar-saldo")
    Boolean aumentarSaldo(@RequestBody SaldoDTO saldoDTO);
}