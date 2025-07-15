package com.reservation.hotel_service.dto;

import lombok.Data;

@Data
public class HotelBookingRequestDTO {
    private Long clientId;
    private String hotel;
    private String status;
}