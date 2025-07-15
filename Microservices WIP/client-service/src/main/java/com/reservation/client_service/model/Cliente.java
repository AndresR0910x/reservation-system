package com.reservation.client_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "clientes_vuelos")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;
    
    @Column(name = "limite_gasto", nullable = false)
    private BigDecimal limiteGasto;
}