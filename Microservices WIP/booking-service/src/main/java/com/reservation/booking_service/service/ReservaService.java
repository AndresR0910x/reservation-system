package com.reservation.booking_service.service;


import com.reservation.booking_service.dto.*;
import com.reservation.booking_service.model.*;
import com.reservation.booking_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final HotelServiceClient hotelServiceClient;
    private final VueloServiceClient vueloServiceClient;
    private final PagoServiceClient pagoServiceClient;
    private final ClienteServiceClient clienteServiceClient; // Nuevo cliente
    
    @Transactional
    public ReservaResponseDTO crearReserva(ReservaDTO reservaDTO) {
        // Crear reserva inicial
        Reserva reserva = new Reserva();
        reserva.setIdCliente(reservaDTO.getIdCliente());
        reserva.setDestino(reservaDTO.getDestino());
        reserva.setNombreHotel(reservaDTO.getNombreHotel());
        reserva.setEstado(Reserva.EstadoReserva.PENDIENTE);
        reserva = reservaRepository.save(reserva);
        
        try {
            // Reservar hotel
            HotelReservaDTO hotelReservaDTO = new HotelReservaDTO();
            hotelReservaDTO.setIdReserva(reserva.getId());
            hotelReservaDTO.setNombreHotel(reserva.getNombreHotel());
            hotelReservaDTO.setIdCliente(reserva.getIdCliente());
            
            HotelReservaResponseDTO hotelResponse = hotelServiceClient.reservarHotel(hotelReservaDTO);
            reserva.setIdReservaHotel(hotelResponse.getId());
            
            // Reservar vuelo
            VueloReservaDTO vueloReservaDTO = new VueloReservaDTO();
            vueloReservaDTO.setIdReserva(reserva.getId());
            vueloReservaDTO.setDestino(reserva.getDestino());
            vueloReservaDTO.setIdCliente(reserva.getIdCliente());
            
            VueloReservaResponseDTO vueloResponse = vueloServiceClient.reservarVuelo(vueloReservaDTO);
            reserva.setIdReservaVuelo(vueloResponse.getId());
            
            // Calcular monto total (suma de hotel y vuelo)
            BigDecimal montoTotal = hotelResponse.getCosto().add(vueloResponse.getCosto());
            reserva.setMontoTotal(montoTotal);
            
            // Verificar saldo antes de procesar pago
            VerificacionSaldoDTO verificacionSaldoDTO = new VerificacionSaldoDTO();
            verificacionSaldoDTO.setIdCliente(reserva.getIdCliente());
            verificacionSaldoDTO.setMontoAReservar(montoTotal);
            
            VerificacionSaldoResponseDTO verificacionSaldo = 
                clienteServiceClient.verificarSaldo(verificacionSaldoDTO);
            
            if (!verificacionSaldo.isSaldoSuficiente() || !verificacionSaldo.isDentroDeLimite()) {
                cancelarReservas(reserva);
                reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
                reservaRepository.save(reserva);
                throw new RuntimeException("No se puede completar la reserva: " + 
                    verificacionSaldo.getMensaje());
            }
            
            // Procesar pago
            PagoDTO pagoDTO = new PagoDTO();
            pagoDTO.setIdReserva(reserva.getId());
            pagoDTO.setIdCliente(reserva.getIdCliente());
            pagoDTO.setMonto(montoTotal);
            
            PagoResponseDTO pagoResponse = pagoServiceClient.procesarPago(pagoDTO);
            
            if (pagoResponse.isExitoso()) {
                // Reducir saldo del cliente
                SaldoDTO saldoDTO = new SaldoDTO();
                saldoDTO.setIdCliente(reserva.getIdCliente());
                saldoDTO.setMonto(montoTotal);
                
                boolean saldoReducido = clienteServiceClient.reducirSaldo(saldoDTO);
                
                if (!saldoReducido) {
                    // Revertir todo si no se pudo reducir el saldo
                    cancelarReservas(reserva);
                    reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
                    reservaRepository.save(reserva);
                    throw new RuntimeException("Error al reducir el saldo del cliente");
                }
                
                reserva.setEstado(Reserva.EstadoReserva.PAGADA);
            } else {
                cancelarReservas(reserva);
                reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
            }
            
            return mapToResponseDTO(reservaRepository.save(reserva));
        } catch (Exception e) {
            cancelarReservas(reserva);
            reserva.setEstado(Reserva.EstadoReserva.CANCELADA);
            reservaRepository.save(reserva);
            throw new RuntimeException("Error al procesar la reserva: " + e.getMessage(), e);
        }
    }
    
    private void cancelarReservas(Reserva reserva) {
    try {
        // Cancelar reserva de hotel si existe
        if (reserva.getIdReservaHotel() != null) {
            CancelacionDTO cancelacionHotel = new CancelacionDTO();
            cancelacionHotel.setIdReserva(reserva.getIdReservaHotel());
            cancelacionHotel.setIdCliente(reserva.getIdCliente());
            hotelServiceClient.cancelarReservaHotel(cancelacionHotel);
        }
        
        // Cancelar reserva de vuelo si existe
        if (reserva.getIdReservaVuelo() != null) {
            CancelacionDTO cancelacionVuelo = new CancelacionDTO();
            cancelacionVuelo.setIdReserva(reserva.getIdReservaVuelo());
            cancelacionVuelo.setIdCliente(reserva.getIdCliente());
            vueloServiceClient.cancelarReservaVuelo(cancelacionVuelo);
        }
        
        // Reembolsar saldo si la reserva estaba pagada
        if (reserva.getEstado() == Reserva.EstadoReserva.PAGADA) {
            SaldoDTO saldoDTO = new SaldoDTO();
            saldoDTO.setIdCliente(reserva.getIdCliente());
            saldoDTO.setMonto(reserva.getMontoTotal());
            clienteServiceClient.aumentarSaldo(saldoDTO);
        }
    } catch (Exception e) {
        // Loggear el error pero no lanzar excepción para no interrumpir el flujo principal
        System.err.println("Error al cancelar reservas: " + e.getMessage());
    }
}
    
    private ReservaResponseDTO mapToResponseDTO(Reserva reserva) {
        ReservaResponseDTO response = new ReservaResponseDTO();
        response.setId(reserva.getId());
        response.setIdCliente(reserva.getIdCliente());
        response.setDestino(reserva.getDestino());
        response.setNombreHotel(reserva.getNombreHotel());
        response.setMontoTotal(reserva.getMontoTotal());
        response.setEstado(reserva.getEstado());
        response.setIdReservaHotel(reserva.getIdReservaHotel());
        response.setIdReservaVuelo(reserva.getIdReservaVuelo());
        return response;
    }
}