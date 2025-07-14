package com.reservation.booking_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "client_id", nullable = false)
    private Long clientId;
    
    @Column(name = "origin", nullable = false)
    private String origin;
    
    @Column(name = "destination", nullable = false)
    private String destination;
    
    @Column(name = "flight_id", nullable = false)
    private Long flightId;
    
    @Column(name = "hotel_id", nullable = false)
    private Long hotelId;
    
    @Column(name = "seats_number", nullable = false)
    private Integer seatsNumber;
    
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
    
    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;
    
    @Column(name = "status", nullable = false)
    private String status; // PENDING, CONFIRMED, CANCELLED
    
    // Getters and setters
}