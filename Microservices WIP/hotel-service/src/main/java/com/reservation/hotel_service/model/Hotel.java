package com.reservation.hotel_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "hotel_vuelos")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
    
    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Habitacion> habitaciones;
    
    @Column(name = "disponible", nullable = false)
    private boolean disponible = true;
}