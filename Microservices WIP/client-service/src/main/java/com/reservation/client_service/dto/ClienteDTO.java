package com.reservation.client_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ClienteDTO {
    private String nombre;
    private String email;
    private BigDecimal saldo;
    private BigDecimal limiteGasto;
}