package com.reservation.aeroline_service.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.reservation.aeroline_service.dto.ClientBalanceDTO;
import com.reservation.aeroline_service.dto.ClientDTO;

@FeignClient(name = "cliente-service")
public interface ClientServiceClient {
    
    @GetMapping("/api/clients/{id}")
    ClientDTO getClientById(@PathVariable Long id);
    
    @GetMapping("/api/clients/{id}/balance")
    ClientBalanceDTO getClientBalance(@PathVariable Long id);
    
    // ... otros métodos
}