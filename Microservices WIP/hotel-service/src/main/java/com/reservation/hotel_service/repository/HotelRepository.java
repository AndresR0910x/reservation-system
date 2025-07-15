package com.reservation.hotel_service.repository;

import com.reservation.hotel_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Optional<Hotel> findByNombre(String nombre);
}