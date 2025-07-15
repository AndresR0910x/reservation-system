package com.reservation.hotel_service.service;

import com.reservation.hotel_service.dto.*;
import com.reservation.hotel_service.model.*;
import com.reservation.hotel_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;
    private final HabitacionRepository habitacionRepository;
    private final ReservaHotelRepository reservaHotelRepository;
    
    public Hotel crearHotel(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setNombre(hotelDTO.getNombre());
        hotel.setUbicacion(hotelDTO.getUbicacion());
        hotel.setDescripcion(hotelDTO.getDescripcion());
        return hotelRepository.save(hotel);
    }
    
    public Habitacion agregarHabitacion(HabitacionDTO habitacionDTO) {
        Hotel hotel = hotelRepository.findById(habitacionDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        
        Habitacion habitacion = new Habitacion();
        habitacion.setTipo(habitacionDTO.getTipo());
        habitacion.setCapacidad(habitacionDTO.getCapacidad());
        habitacion.setPrecioPorNoche(habitacionDTO.getPrecioPorNoche());
        habitacion.setHotel(hotel);
        
        return habitacionRepository.save(habitacion);
    }
    
    @Transactional
    public HotelReservaResponseDTO reservarHotel(HotelReservaDTO reservaDTO) {
        // Buscar hotel por nombre
        Hotel hotel = hotelRepository.findByNombre(reservaDTO.getNombreHotel())
                .orElseThrow(() -> new RuntimeException("Hotel no encontrado"));
        
        // Buscar habitaciones disponibles
        List<Habitacion> habitacionesDisponibles = habitacionRepository.findByHotelAndDisponibleTrue(hotel);
        
        if (habitacionesDisponibles.isEmpty()) {
            throw new RuntimeException("No hay habitaciones disponibles en este hotel");
        }
        
        // Seleccionar la primera habitación disponible (podría implementar lógica más compleja)
        Habitacion habitacion = habitacionesDisponibles.get(0);
        
        // Crear reserva de hotel
        ReservaHotel reserva = new ReservaHotel();
        reserva.setIdReserva(reservaDTO.getIdReserva());
        reserva.setIdCliente(reservaDTO.getIdCliente());
        reserva.setHotel(hotel);
        reserva.setHabitacion(habitacion);
        
        // Fechas por defecto (podría recibirse como parámetro)
        reserva.setFechaInicio(LocalDate.now().plusDays(1));
        reserva.setFechaFin(LocalDate.now().plusDays(3));
        
        // Calcular costo total (2 noches por defecto)
        long noches = reserva.getFechaFin().toEpochDay() - reserva.getFechaInicio().toEpochDay();
        BigDecimal costoTotal = habitacion.getPrecioPorNoche().multiply(BigDecimal.valueOf(noches));
        reserva.setCostoTotal(costoTotal);
        reserva.setEstado(ReservaHotel.EstadoReserva.PENDIENTE);
        
        // Marcar habitación como no disponible
        habitacion.setDisponible(false);
        habitacionRepository.save(habitacion);
        
        reserva = reservaHotelRepository.save(reserva);
        
        return mapToResponseDTO(reserva);
    }
    
    @Transactional
    public boolean cancelarReserva(CancelacionDTO cancelacionDTO) {
        int updated = reservaHotelRepository.cancelarReserva(
            cancelacionDTO.getIdReserva(), 
            cancelacionDTO.getIdCliente()
        );
        
        if (updated > 0) {
            // Marcar habitación como disponible nuevamente
            ReservaHotel reserva = reservaHotelRepository.findById(cancelacionDTO.getIdReserva())
                    .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
            
            Habitacion habitacion = reserva.getHabitacion();
            habitacion.setDisponible(true);
            habitacionRepository.save(habitacion);
            
            return true;
        }
        
        return false;
    }
    
    private HotelReservaResponseDTO mapToResponseDTO(ReservaHotel reserva) {
        HotelReservaResponseDTO response = new HotelReservaResponseDTO();
        response.setId(reserva.getId());
        response.setIdReserva(reserva.getIdReserva());
        response.setNombreHotel(reserva.getHotel().getNombre());
        response.setIdCliente(reserva.getIdCliente());
        response.setCosto(reserva.getCostoTotal());
        response.setEstado(reserva.getEstado().name());
        return response;
    }
}