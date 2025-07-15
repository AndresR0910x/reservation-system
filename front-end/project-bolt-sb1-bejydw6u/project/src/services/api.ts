import { ReservaFormData, Reserva } from '../types';

// Mock API - Reemplaza con tu URL real
const API_BASE_URL = 'https://tu-api.com/api';

export const crearReserva = async (data: ReservaFormData): Promise<Reserva> => {
  // Simular llamada a API real
  // En producción, reemplaza esto con:
  // const response = await fetch(`${API_BASE_URL}/reservas`, {
  //   method: 'POST',
  //   headers: { 'Content-Type': 'application/json' },
  //   body: JSON.stringify(data)
  // });
  
  // Mock - simular respuesta exitosa o fallida aleatoriamente
  const esExitosa = Math.random() > 0.3;
  
  await new Promise(resolve => setTimeout(resolve, 1000)); // Simular latencia
  
  const reserva: Reserva = {
    id: `reserva-${Date.now()}`,
    ...data,
    estado: esExitosa ? 'exitosa' : 'fallida',
    fecha_creacion: new Date().toISOString(),
    error: esExitosa ? undefined : 'Error de validación en el servidor'
  };
  
  if (!esExitosa) {
    throw new Error('Error al crear la reserva');
  }
  
  return reserva;
};