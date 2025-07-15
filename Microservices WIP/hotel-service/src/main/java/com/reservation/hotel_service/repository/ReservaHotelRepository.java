package com.reservation.hotel_service.repository;

import com.reservation.hotel_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReservaHotelRepository extends JpaRepository<ReservaHotel, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE ReservaHotel r SET r.estado = 'CANCELADA' WHERE r.id = :id AND r.idCliente = :idCliente")
    int cancelarReserva(@Param("id") Long id, @Param("idCliente") Long idCliente);
}