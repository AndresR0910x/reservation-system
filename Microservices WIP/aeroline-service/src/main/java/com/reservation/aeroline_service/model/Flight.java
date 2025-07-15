package com.reservation.aeroline_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
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
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
    
    @Column(nullable = false)
    private Boolean disponibilidad;
    
    @Column(name = "asientos_disponibles", nullable = false)
    private Integer asientosDisponibles;
    
    @Column(nullable = false)
    private String status; // AVAILABLE, RESERVED, CONFIRMED, CANCELLED
    
    @Column(name = "client_id") // Cambiado de reserved_by a client_id
    private Long clientId;
    
    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;
    
    @Column(name = "cancellation_reason")
    private String cancellationReason;
    
    // Getters and setters
}