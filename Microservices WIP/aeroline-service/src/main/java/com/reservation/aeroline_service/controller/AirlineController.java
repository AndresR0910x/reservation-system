package com.reservation.aeroline_service.controller;

import com.reservation.aeroline_service.dto.*;
import com.reservation.aeroline_service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;
    
    @PostMapping
    public ResponseEntity<AirlineDTO> createAirline(@RequestBody AirlineDTO airlineDTO) {
        AirlineDTO createdAirline = airlineService.createAirline(airlineDTO);
        return new ResponseEntity<>(createdAirline, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<AirlineDTO>> getAllAirlines() {
        List<AirlineDTO> airlines = airlineService.getAllAirlines();
        return ResponseEntity.ok(airlines);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AirlineDTO> getAirlineById(@PathVariable Long id) {
        AirlineDTO airline = airlineService.getAirlineById(id);
        return ResponseEntity.ok(airline);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AirlineDTO> updateAirline(@PathVariable Long id, @RequestBody AirlineDTO airlineDTO) {
        AirlineDTO updatedAirline = airlineService.updateAirline(id, airlineDTO);
        return ResponseEntity.ok(updatedAirline);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirline(@PathVariable Long id) {
        airlineService.deleteAirline(id);
        return ResponseEntity.noContent().build();
    }
}