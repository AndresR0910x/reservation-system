package com.reservation.client_service.controller;

import com.reservation.client_service.dto.*;
import com.reservation.client_service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientCreateDTO clientCreateDTO) {
        ClientDTO createdClient = clientService.createClient(clientCreateDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }
    
    @GetMapping("/{id}/balance")
    public ResponseEntity<ClientBalanceDTO> getClientBalance(@PathVariable Long id) {
        ClientBalanceDTO balance = clientService.getClientBalance(id);
        return ResponseEntity.ok(balance);
    }
    
    @PutMapping("/{id}/balance")
    public ResponseEntity<ClientBalanceDTO> updateClientBalance(
            @PathVariable Long id, @RequestBody ClientBalanceDTO balanceDTO) {
        ClientBalanceDTO updatedBalance = clientService.updateClientBalance(id, balanceDTO);
        return ResponseEntity.ok(updatedBalance);
    }
    
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<ClientDTO> deactivateClient(@PathVariable Long id) {
        ClientDTO client = clientService.deactivateClient(id);
        return ResponseEntity.ok(client);
    }
    
    @PostMapping("/{id}/activate")
    public ResponseEntity<ClientDTO> activateClient(@PathVariable Long id) {
        ClientDTO client = clientService.activateClient(id);
        return ResponseEntity.ok(client);
    }
}