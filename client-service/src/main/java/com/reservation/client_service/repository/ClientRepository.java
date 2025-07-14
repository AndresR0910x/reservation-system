package com.reservation.client_service.repository;

import com.reservation.client_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
    
    @Transactional
    @Modifying
    @Query("UPDATE Client c SET c.balance = :balance WHERE c.id = :id")
    void updateBalance(@Param("id") Long id, @Param("balance") BigDecimal balance);
}