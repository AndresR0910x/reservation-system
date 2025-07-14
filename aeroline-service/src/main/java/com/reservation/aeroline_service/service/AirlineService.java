package com.reservation.aeroline_service.service;

import com.reservation.aeroline_service.dto.*;
import com.reservation.aeroline_service.model.*;
import com.reservation.aeroline_service.exception.*;
import com.reservation.aeroline_service.mapper.*;
import com.reservation.aeroline_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;
    
    public AirlineDTO createAirline(AirlineDTO airlineDTO) {
        Airline airline = airlineMapper.toEntity(airlineDTO);
        Airline savedAirline = airlineRepository.save(airline);
        return airlineMapper.toDTO(savedAirline);
    }
    
    public List<AirlineDTO> getAllAirlines() {
        return airlineRepository.findAll().stream()
                .map(airlineMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public AirlineDTO getAirlineById(Long id) {
        Airline airline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + id));
        return airlineMapper.toDTO(airline);
    }
    
    public AirlineDTO updateAirline(Long id, AirlineDTO airlineDTO) {
        Airline existingAirline = airlineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airline not found with id: " + id));
        
        existingAirline.setNombre(airlineDTO.getNombre());
        existingAirline.setCodigo(airlineDTO.getCodigo());
        
        Airline updatedAirline = airlineRepository.save(existingAirline);
        return airlineMapper.toDTO(updatedAirline);
    }
    
    public void deleteAirline(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Airline not found with id: " + id);
        }
        airlineRepository.deleteById(id);
    }
}