package com.reservation.client_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientCreateDTO {
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String phoneNumber;
    
    @NotBlank
    private String address;
    
    @NotNull
    @DecimalMin("0.0")
    private BigDecimal initialBalance;
}