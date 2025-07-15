package com.reservation.aeroline_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private BigDecimal balance;
    private Boolean active;
}
