package com.reservation.Pago_service.repository;

import com.reservation.Pago_service.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByReservationId(Long reservationId);
    Optional<Payment> findByTransactionId(String transactionId);
}