package com.reservation.Pago_service.service;

import com.reservation.Pago_service.dto.*;
import com.reservation.Pago_service.model.*;
import com.reservation.Pago_service.exception.*;
import com.reservation.Pago_service.mapper.*;
import com.reservation.Pago_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ClientServiceClient clientServiceClient;

    @Transactional
    public PaymentResponseDTO processPayment(PaymentProcessDTO paymentProcessDTO) {
        // 1. Verificar saldo del cliente
        ClientBalanceDTO clientBalance = clientServiceClient.getClientBalance(paymentProcessDTO.getClientId());
        
        if (clientBalance.getBalance() < paymentProcessDTO.getAmount()) {
            throw new InsufficientBalanceException("Saldo insuficiente para realizar el pago");
        }

        // 2. Actualizar saldo del cliente
        ClientBalanceDTO updatedBalance = new ClientBalanceDTO();
        updatedBalance.setBalance(clientBalance.getBalance() - paymentProcessDTO.getAmount());
        clientServiceClient.updateClientBalance(paymentProcessDTO.getClientId(), updatedBalance);

        // 3. Registrar el pago
        Payment payment = new Payment();
        payment.setClientId(paymentProcessDTO.getClientId());
        payment.setAmount(paymentProcessDTO.getAmount());
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId(generateTransactionId());
        payment.setPaymentMethod("WALLET");
        payment.setReservationId(paymentProcessDTO.getReservationId());

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toDTO(savedPayment);
    }

    @Transactional
    public PaymentResponseDTO processRefund(Long paymentId, String reason) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Pago no encontrado"));

        if (!"COMPLETED".equals(payment.getStatus())) {
            throw new InvalidPaymentOperationException("Solo pagos COMPLETED pueden ser reembolsados");
        }

        // 1. Reembolsar al cliente
        ClientBalanceDTO clientBalance = clientServiceClient.getClientBalance(payment.getClientId());
        ClientBalanceDTO updatedBalance = new ClientBalanceDTO();
        updatedBalance.setBalance(clientBalance.getBalance() + payment.getAmount());
        clientServiceClient.updateClientBalance(payment.getClientId(), updatedBalance);

        // 2. Actualizar estado del pago
        payment.setStatus("REFUNDED");
        payment.setRefundReason(reason);
        payment.setRefundDate(LocalDateTime.now());

        Payment updatedPayment = paymentRepository.save(payment);

        return paymentMapper.toDTO(updatedPayment);
    }

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}