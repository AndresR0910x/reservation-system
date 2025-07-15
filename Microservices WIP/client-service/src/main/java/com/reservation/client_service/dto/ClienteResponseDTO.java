package com.reservation.client_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private BigDecimal saldo;
    private BigDecimal limiteGasto;
}