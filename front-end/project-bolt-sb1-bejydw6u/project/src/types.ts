export interface Reserva {
  id: string;
  cliente: string;
  vuelo_destino: string;
  hotel_nombre: string;
  monto_total: number;
  estado: 'exitosa' | 'fallida';
  fecha_creacion: string;
  error?: string;
}

export interface ReservaFormData {
  cliente: string;
  vuelo_destino: string;
  hotel_nombre: string;
  monto_total: number;
}