package com.reservation.client_service.repository;

import com.reservation.client_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.saldo = c.saldo - :monto WHERE c.id = :id AND c.saldo >= :monto")
    int reducirSaldo(@Param("id") Long id, @Param("monto") BigDecimal monto);
    
    @Modifying
    @Transactional
    @Query("UPDATE Cliente c SET c.saldo = c.saldo + :monto WHERE c.id = :id")
    int aumentarSaldo(@Param("id") Long id, @Param("monto") BigDecimal monto);
}