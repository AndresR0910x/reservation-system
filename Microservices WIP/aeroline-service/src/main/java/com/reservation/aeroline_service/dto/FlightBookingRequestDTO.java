package com.reservation.aeroline_service.dto;

import lombok.Data;

@Data
public class FlightBookingRequestDTO {
    private Long clientId;
    private String destino;
    private String status;
}