package com.reservation.aeroline_service.dto;

import lombok.Data;

@Data
public class FlightStatusUpdateDTO {
    private String status;
    private String reason;
}