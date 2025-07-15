package com.reservation.client_service.controller;

import com.reservation.client_service.dto.*;
import com.reservation.client_service.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        ClienteResponseDTO response = clienteService.crearCliente(clienteDTO);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerCliente(@PathVariable Long id) {
        ClienteResponseDTO response = clienteService.obtenerCliente(id);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/verificar-saldo")
    public ResponseEntity<VerificacionSaldoResponseDTO> verificarSaldo(
            @RequestBody VerificacionSaldoDTO verificacionDTO) {
        VerificacionSaldoResponseDTO response = clienteService.verificarSaldo(verificacionDTO);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/reducir-saldo")
    public ResponseEntity<Boolean> reducirSaldo(@RequestBody SaldoDTO saldoDTO) {
        boolean resultado = clienteService.reducirSaldo(saldoDTO);
        return ResponseEntity.ok(resultado);
    }
    
    @PostMapping("/aumentar-saldo")
    public ResponseEntity<Boolean> aumentarSaldo(@RequestBody SaldoDTO saldoDTO) {
        boolean resultado = clienteService.aumentarSaldo(saldoDTO);
        return ResponseEntity.ok(resultado);
    }
}