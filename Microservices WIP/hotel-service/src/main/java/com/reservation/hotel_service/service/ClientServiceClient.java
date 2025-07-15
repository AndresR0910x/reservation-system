package com.reservation.hotel_service.service;

import com.reservation.hotel_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cliente-service")
public interface ClientServiceClient {
    
    @GetMapping("/api/clients/{id}")
    ClientDTO getClientById(@PathVariable Long id);
    
    @GetMapping("/api/clients/{id}/balance")
    ClientBalanceDTO getClientBalance(@PathVariable Long id);
}