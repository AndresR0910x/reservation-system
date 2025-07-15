package com.reservation.hotel_service.dto;

import lombok.Data;

@Data
public class HotelStatusUpdateDTO {
    private String status;
    private String reason;
}