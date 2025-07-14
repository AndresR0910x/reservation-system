package com.reservation.client_service.service;

import com.reservation.client_service.dto.*;
import com.reservation.client_service.model.*;
import com.reservation.client_service.exception.*;
import com.reservation.client_service.mapper.*;
import com.reservation.client_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    
    public ClientDTO createClient(ClientCreateDTO clientCreateDTO) {
        Client client = clientMapper.toEntity(clientCreateDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDTO(savedClient);
    }
    
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
        return clientMapper.toDTO(client);
    }
    
    public ClientBalanceDTO getClientBalance(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
        
        ClientBalanceDTO balanceDTO = new ClientBalanceDTO();
        balanceDTO.setClientId(client.getId());
        balanceDTO.setBalance(client.getBalance());
        return balanceDTO;
    }
    
    @Transactional
    public ClientBalanceDTO updateClientBalance(Long id, ClientBalanceDTO balanceDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
        
        if (balanceDTO.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Balance cannot be negative");
        }
        
        client.setBalance(balanceDTO.getBalance());
        clientRepository.save(client);
        
        ClientBalanceDTO response = new ClientBalanceDTO();
        response.setClientId(client.getId());
        response.setBalance(client.getBalance());
        return response;
    }
    
    @Transactional
    public ClientDTO deactivateClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
        
        client.setActive(false);
        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDTO(updatedClient);
    }
    
    @Transactional
    public ClientDTO activateClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with id: " + id));
        
        client.setActive(true);
        Client updatedClient = clientRepository.save(client);
        return clientMapper.toDTO(updatedClient);
    }
}