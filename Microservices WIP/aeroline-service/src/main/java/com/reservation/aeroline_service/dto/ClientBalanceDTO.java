package com.reservation.aeroline_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientBalanceDTO {
    private Long clientId;
    private BigDecimal balance;
}
