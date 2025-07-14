package com.reservation.hotel_service.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String ubicacion;
    
    @Column(name = "cuartos_disponibles", nullable = false)
    private Integer cuartosDisponibles;
    
    @Column(name = "precio_por_noche", nullable = false)
    private Double precioPorNoche;
    
    @Column(nullable = false)
    private Boolean disponibilidad;
    
    // Getters and setters
}