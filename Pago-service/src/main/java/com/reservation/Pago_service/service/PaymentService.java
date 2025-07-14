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
    public PaymentResponseDTO processPayment(PaymentRequestDTO paymentRequest) {
        // Validar si ya existe un pago para esta reserva
        if (paymentRepository.existsByReservationId(paymentRequest.getReservationId())) {
            throw new PaymentAlreadyExistsException("Payment already exists for this reservation");
        }
        
        // Verificar saldo del cliente
        ClientBalanceDTO clientBalance = clientServiceClient.getClientBalance(paymentRequest.getClientId());
        
        if (clientBalance.getBalance() == null || clientBalance.getBalance() < paymentRequest.getAmount()) {
            // Registrar pago fallido
            Payment failedPayment = createFailedPayment(paymentRequest);
            throw new InsufficientBalanceException("Insufficient client balance");
        }
        
        // Actualizar saldo del cliente
        ClientBalanceDTO updatedBalance = new ClientBalanceDTO();
        updatedBalance.setBalance(clientBalance.getBalance() - paymentRequest.getAmount());
        clientServiceClient.updateClientBalance(paymentRequest.getClientId(), updatedBalance);
        
        // Registrar pago exitoso
        Payment payment = new Payment();
        payment.setReservationId(paymentRequest.getReservationId());
        payment.setClientId(paymentRequest.getClientId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setStatus("COMPLETED");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setTransactionId(generateTransactionId());
        
        Payment savedPayment = paymentRepository.save(payment);
        
        return paymentMapper.toDTO(savedPayment);
    }
    
    private Payment createFailedPayment(PaymentRequestDTO paymentRequest) {
        Payment payment = new Payment();
        payment.setReservationId(paymentRequest.getReservationId());
        payment.setClientId(paymentRequest.getClientId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setStatus("FAILED");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        return paymentRepository.save(payment);
    }
    
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public PaymentResponseDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return paymentMapper.toDTO(payment);
    }
    
    public PaymentResponseDTO getPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with transaction id: " + transactionId));
        return paymentMapper.toDTO(payment);
    }
    
    @Transactional
    public PaymentResponseDTO processRefund(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + paymentId));
        
        if (!"COMPLETED".equals(payment.getStatus())) {
            throw new InvalidPaymentOperationException("Only completed payments can be refunded");
        }
        
        // Devolver saldo al cliente
        ClientBalanceDTO clientBalance = clientServiceClient.getClientBalance(payment.getClientId());
        ClientBalanceDTO updatedBalance = new ClientBalanceDTO();
        updatedBalance.setBalance(clientBalance.getBalance() + payment.getAmount());
        clientServiceClient.updateClientBalance(payment.getClientId(), updatedBalance);
        
        // Actualizar estado del pago
        payment.setStatus("REFUNDED");
        Payment updatedPayment = paymentRepository.save(payment);
        
        return paymentMapper.toDTO(updatedPayment);
    }
}