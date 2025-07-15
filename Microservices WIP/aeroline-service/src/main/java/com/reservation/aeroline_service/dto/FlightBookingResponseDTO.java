package com.reservation.aeroline_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightBookingResponseDTO {
    private Long flightId;
    private String reservationId;
    private String status;
    private LocalDateTime reservationDate;
    private String message;
}