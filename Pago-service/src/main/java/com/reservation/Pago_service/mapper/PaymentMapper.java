package com.reservation.Pago_service.mapper;

import com.reservation.Pago_service.dto.*;
import com.reservation.Pago_service.model.*;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentResponseDTO toDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setId(payment.getId());
        dto.setReservationId(payment.getReservationId());
        dto.setClientId(payment.getClientId());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentMethod(payment.getPaymentMethod());
        dto.setTransactionId(payment.getTransactionId());
        return dto;
    }
}