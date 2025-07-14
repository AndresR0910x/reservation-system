package com.reservation.Pago_service.service;

import com.reservation.Pago_service.dto.*;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cliente-service")
public interface ClientServiceClient {
    
    @GetMapping("/api/clients/{id}/balance")
    @CircuitBreaker(name = "clientService", fallbackMethod = "getClientBalanceFallback")
    ClientBalanceDTO getClientBalance(@PathVariable Long id);
    
    @PutMapping("/api/clients/{id}/balance")
    @CircuitBreaker(name = "clientService", fallbackMethod = "updateClientBalanceFallback")
    ClientBalanceDTO updateClientBalance(@PathVariable Long id, @RequestBody ClientBalanceDTO balanceDTO);
    
    default ClientBalanceDTO getClientBalanceFallback(Long id, Exception e) {
        // Lógica de fallback
        return new ClientBalanceDTO();
    }
    
    default ClientBalanceDTO updateClientBalanceFallback(Long id, ClientBalanceDTO balanceDTO, Exception e) {
        // Lógica de fallback
        return new ClientBalanceDTO();
    }
}