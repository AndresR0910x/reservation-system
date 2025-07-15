package com.reservation.client_service.service;

import com.reservation.client_service.dto.*;
import com.reservation.client_service.model.*;
import com.reservation.client_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    
    public ClienteResponseDTO crearCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setSaldo(clienteDTO.getSaldo());
        cliente.setLimiteGasto(clienteDTO.getLimiteGasto() != null ? 
                              clienteDTO.getLimiteGasto() : new BigDecimal("1000"));
        
        cliente = clienteRepository.save(cliente);
        return mapToResponseDTO(cliente);
    }
    
    public ClienteResponseDTO obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }
    
    @Transactional
    public VerificacionSaldoResponseDTO verificarSaldo(VerificacionSaldoDTO verificacionDTO) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(verificacionDTO.getIdCliente());
        
        if (clienteOpt.isEmpty()) {
            VerificacionSaldoResponseDTO response = new VerificacionSaldoResponseDTO();
            response.setSaldoSuficiente(false);
            response.setDentroDeLimite(false);
            response.setMensaje("Cliente no encontrado");
            return response;
        }
        
        Cliente cliente = clienteOpt.get();
        BigDecimal montoAReservar = verificacionDTO.getMontoAReservar();
        
        boolean saldoSuficiente = cliente.getSaldo().compareTo(montoAReservar) >= 0;
        boolean dentroDeLimite = montoAReservar.compareTo(cliente.getLimiteGasto()) <= 0;
        
        String mensaje = saldoSuficiente && dentroDeLimite ? 
            "Saldo suficiente y dentro del límite de gasto" :
            !saldoSuficiente ? "Saldo insuficiente" : "Supera el límite de gasto permitido";
        
        VerificacionSaldoResponseDTO response = new VerificacionSaldoResponseDTO();
        response.setSaldoSuficiente(saldoSuficiente);
        response.setDentroDeLimite(dentroDeLimite);
        response.setMensaje(mensaje);
        
        return response;
    }
    
    @Transactional
    public boolean reducirSaldo(SaldoDTO saldoDTO) {
        int updated = clienteRepository.reducirSaldo(
            saldoDTO.getIdCliente(), 
            saldoDTO.getMonto()
        );
        return updated > 0;
    }
    
    @Transactional
    public boolean aumentarSaldo(SaldoDTO saldoDTO) {
        int updated = clienteRepository.aumentarSaldo(
            saldoDTO.getIdCliente(), 
            saldoDTO.getMonto()
        );
        return updated > 0;
    }
    
    private ClienteResponseDTO mapToResponseDTO(Cliente cliente) {
        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setEmail(cliente.getEmail());
        response.setSaldo(cliente.getSaldo());
        response.setLimiteGasto(cliente.getLimiteGasto());
        return response;
    }
}