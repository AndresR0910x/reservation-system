package com.reservation.booking_service.repository;

import com.reservation.booking_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}