package com.reservation.hotel_service.controller;

import com.reservation.hotel_service.dto.*;
import com.reservation.hotel_service.model.*;
import com.reservation.hotel_service.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
    private final HotelService hotelService;
    
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }
    
    @PostMapping
    public ResponseEntity<Hotel> crearHotel(@RequestBody HotelDTO hotelDTO) {
        Hotel hotel = hotelService.crearHotel(hotelDTO);
        return ResponseEntity.ok(hotel);
    }
    
    @PostMapping("/habitacion")
    public ResponseEntity<Habitacion> agregarHabitacion(@RequestBody HabitacionDTO habitacionDTO) {
        Habitacion habitacion = hotelService.agregarHabitacion(habitacionDTO);
        return ResponseEntity.ok(habitacion);
    }
    
    @PostMapping("/reservar")
    public ResponseEntity<HotelReservaResponseDTO> reservarHotel(@RequestBody HotelReservaDTO reservaDTO) {
        HotelReservaResponseDTO response = hotelService.reservarHotel(reservaDTO);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/cancelar")
    public ResponseEntity<Boolean> cancelarReserva(@RequestBody CancelacionDTO cancelacionDTO) {
        boolean resultado = hotelService.cancelarReserva(cancelacionDTO);
        return ResponseEntity.ok(resultado);
    }
}