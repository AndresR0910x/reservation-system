package com.reservation.booking_service.repository;

import com.reservation.booking_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}