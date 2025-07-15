package com.reservation.booking_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;
    
    @Column(name = "destino", nullable = false)
    private String destino;
    
    @Column(name = "nombre_hotel", nullable = false)
    private String nombreHotel;
    
    @Column(name = "monto_total")
    private BigDecimal montoTotal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoReserva estado;
    
    @Column(name = "id_reserva_hotel")
    private Long idReservaHotel;
    
    @Column(name = "id_reserva_vuelo")
    private Long idReservaVuelo;
    
    public enum EstadoReserva {
        PENDIENTE, PAGADA, CANCELADA
    }
}