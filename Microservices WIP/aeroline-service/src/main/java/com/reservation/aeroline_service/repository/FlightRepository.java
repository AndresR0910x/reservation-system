package com.reservation.aeroline_service.repository;


import com.reservation.aeroline_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByOrigenAndDestinoAndFechaSalidaAfterAndDisponibilidadTrue(
        String origen, String destino, LocalDateTime fechaSalida);
    
    List<Flight> findByDisponibilidadTrue();

    List<Flight> findByDestinoAndStatusAndDisponibilidadTrue(String destino, String string);
}