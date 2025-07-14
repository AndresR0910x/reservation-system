package com.reservation.hotel_service.service;

import com.reservation.hotel_service.dto.*;
import com.reservation.hotel_service.model.*;
import com.reservation.hotel_service.exception.*;
import com.reservation.hotel_service.mapper.*;
import com.reservation.hotel_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    
    public HotelDTO createHotel(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        Hotel savedHotel = hotelRepository.save(hotel);
        return hotelMapper.toDTO(savedHotel);
    }
    
    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public HotelDTO getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        return hotelMapper.toDTO(hotel);
    }
    
    public List<HotelDTO> getAvailableHotelsByLocation(String location) {
        List<Hotel> hotels = hotelRepository.findByUbicacionAndDisponibilidadTrue(location);
        return hotels.stream()
                .map(hotelMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public HotelDTO updateHotel(Long id, HotelDTO hotelDTO) {
        Hotel existingHotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        
        existingHotel.setNombre(hotelDTO.getNombre());
        existingHotel.setUbicacion(hotelDTO.getUbicacion());
        existingHotel.setCuartosDisponibles(hotelDTO.getCuartosDisponibles());
        existingHotel.setPrecioPorNoche(hotelDTO.getPrecioPorNoche());
        existingHotel.setDisponibilidad(hotelDTO.getDisponibilidad());
        
        Hotel updatedHotel = hotelRepository.save(existingHotel);
        return hotelMapper.toDTO(updatedHotel);
    }
    
    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new ResourceNotFoundException("Hotel not found with id: " + id);
        }
        hotelRepository.deleteById(id);
    }
}