package com.reservation.hotel_service.repository;

import com.reservation.hotel_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    List<Habitacion> findByHotelAndDisponibleTrue(Hotel hotel);
}