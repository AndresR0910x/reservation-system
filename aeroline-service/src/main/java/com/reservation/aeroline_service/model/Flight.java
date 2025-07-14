package com.reservation.aeroline_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "aerolinea_id", nullable = false)
    private Long aerolineaId;
    
    @Column(nullable = false)
    private String origen;
    
    @Column(nullable = false)
    private String destino;
    
    @Column(name = "fecha_salida", nullable = false)
    private LocalDateTime fechaSalida;
    
    @Column(name = "fecha_llegada", nullable = false)
    private LocalDateTime fechaLlegada;
    
    @Column(nullable = false)
    private Double precio;
    
    @Column(nullable = false)
    private Boolean disponibilidad;
    
    @Column(name = "asientos_disponibles", nullable = false)
    private Integer asientosDisponibles;
    
    // Getters and setters
}