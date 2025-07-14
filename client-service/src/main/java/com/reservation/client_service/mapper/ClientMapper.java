package com.reservation.client_service.mapper;

import com.reservation.client_service.dto.*;
import com.reservation.client_service.model.*;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setEmail(client.getEmail());
        dto.setPhoneNumber(client.getPhoneNumber());
        dto.setAddress(client.getAddress());
        dto.setBalance(client.getBalance());
        dto.setActive(client.getActive());
        return dto;
    }
    
    public Client toEntity(ClientCreateDTO dto) {
        Client client = new Client();
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhoneNumber(dto.getPhoneNumber());
        client.setAddress(dto.getAddress());
        client.setBalance(dto.getInitialBalance());
        client.setActive(true);
        return client;
    }
}