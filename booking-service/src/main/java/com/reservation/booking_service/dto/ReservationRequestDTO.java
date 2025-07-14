package com.reservation.booking_service.dto;

import lombok.Data;

@Data
public class ReservationRequestDTO {
    private Long clientId;
    private String origin;
    private String destination;
    private Long flightId;
    private Long hotelId;
    private Integer seatsNumber;
}