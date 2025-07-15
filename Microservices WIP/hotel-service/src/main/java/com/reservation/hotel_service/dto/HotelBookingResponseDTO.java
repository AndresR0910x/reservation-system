package com.reservation.hotel_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HotelBookingResponseDTO {
    private Long hotelId;
    private String reservationId;
    private String status;
    private LocalDateTime reservationDate;
    private String message;
}