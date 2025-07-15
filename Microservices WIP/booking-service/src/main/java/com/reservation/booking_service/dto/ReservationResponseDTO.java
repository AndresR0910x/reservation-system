package com.reservation.booking_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDTO {
    private Long id;
    private Long clientId;
    private String origin;
    private String destination;
    private Long flightId;
    private Long hotelId;
    private Integer seatsNumber;
    private Double totalPrice;
    private LocalDateTime reservationDate;
    private String status;
}