package com.reservation.aeroline_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "airlines")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    @Column(nullable = false)
    private String codigo;
    
    // Getters and setters
}