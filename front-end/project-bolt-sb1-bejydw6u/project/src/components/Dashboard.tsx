import React from 'react';
import { CheckCircle, XCircle, Clock, TrendingUp, TrendingDown, Calendar } from 'lucide-react';
import { Reserva } from '../types';

interface DashboardProps {
  reservas: Reserva[];
}

export const Dashboard: React.FC<DashboardProps> = ({ reservas }) => {
  const reservasExitosas = reservas.filter(r => r.estado === 'exitosa');
  const reservasFallidas = reservas.filter(r => r.estado === 'fallida');
  const tasaExito = reservas.length > 0 ? (reservasExitosas.length / reservas.length) * 100 : 0;

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('es-ES', {
      style: 'currency',
      currency: 'EUR'
    }).format(amount);
  };

  return (
    <div className="max-w-7xl mx-auto space-y-8">
      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-lg">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600">Total Reservas</p>
              <p className="text-2xl font-bold text-gray-900">{reservas.length}</p>
            </div>
            <Clock className="w-8 h-8 text-blue-500" />
          </div>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-lg">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600">Exitosas</p>
              <p className="text-2xl font-bold text-green-600">{reservasExitosas.length}</p>
            </div>
            <TrendingUp className="w-8 h-8 text-green-500" />
          </div>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-lg">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600">Fallidas</p>
              <p className="text-2xl font-bold text-red-600">{reservasFallidas.length}</p>
            </div>
            <TrendingDown className="w-8 h-8 text-red-500" />
          </div>
        </div>

        <div className="bg-white p-6 rounded-xl shadow-lg">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-gray-600">Tasa de Éxito</p>
              <p className="text-2xl font-bold text-blue-600">{tasaExito.toFixed(1)}%</p>
            </div>
            <CheckCircle className="w-8 h-8 text-blue-500" />
          </div>
        </div>
      </div>

      {/* Reservas Lists */}
      <div className="grid grid-cols-1 xl:grid-cols-2 gap-8">
        {/* Reservas Exitosas */}
        <div className="bg-white rounded-xl shadow-lg p-6">
          <div className="flex items-center mb-6">
            <CheckCircle className="w-6 h-6 text-green-500 mr-3" />
            <h3 className="text-xl font-bold text-gray-900">Reservas Exitosas</h3>
            <span className="ml-auto bg-green-100 text-green-800 px-3 py-1 rounded-full text-sm font-medium">
              {reservasExitosas.length}
            </span>
          </div>
          
          <div className="space-y-4 max-h-96 overflow-y-auto">
            {reservasExitosas.length === 0 ? (
              <p className="text-gray-500 text-center py-8">No hay reservas exitosas aún</p>
            ) : (
              reservasExitosas.map((reserva) => (
                <div key={reserva.id} className="border border-green-200 rounded-lg p-4 bg-green-50">
                  <div className="flex justify-between items-start mb-2">
                    <h4 className="font-semibold text-gray-900">{reserva.cliente}</h4>
                    <span className="text-sm text-gray-500 flex items-center">
                      <Calendar className="w-4 h-4 mr-1" />
                      {formatDate(reserva.fecha_creacion)}
                    </span>
                  </div>
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 text-sm text-gray-700">
                    <p><strong>Destino:</strong> {reserva.vuelo_destino}</p>
                    <p><strong>Hotel:</strong> {reserva.hotel_nombre}</p>
                    <p><strong>Monto:</strong> {formatCurrency(reserva.monto_total)}</p>
                    <p><strong>ID:</strong> {reserva.id}</p>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>

        {/* Reservas Fallidas */}
        <div className="bg-white rounded-xl shadow-lg p-6">
          <div className="flex items-center mb-6">
            <XCircle className="w-6 h-6 text-red-500 mr-3" />
            <h3 className="text-xl font-bold text-gray-900">Reservas Fallidas</h3>
            <span className="ml-auto bg-red-100 text-red-800 px-3 py-1 rounded-full text-sm font-medium">
              {reservasFallidas.length}
            </span>
          </div>
          
          <div className="space-y-4 max-h-96 overflow-y-auto">
            {reservasFallidas.length === 0 ? (
              <p className="text-gray-500 text-center py-8">No hay reservas fallidas</p>
            ) : (
              reservasFallidas.map((reserva) => (
                <div key={reserva.id} className="border border-red-200 rounded-lg p-4 bg-red-50">
                  <div className="flex justify-between items-start mb-2">
                    <h4 className="font-semibold text-gray-900">{reserva.cliente}</h4>
                    <span className="text-sm text-gray-500 flex items-center">
                      <Calendar className="w-4 h-4 mr-1" />
                      {formatDate(reserva.fecha_creacion)}
                    </span>
                  </div>
                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 text-sm text-gray-700 mb-2">
                    <p><strong>Destino:</strong> {reserva.vuelo_destino}</p>
                    <p><strong>Hotel:</strong> {reserva.hotel_nombre}</p>
                    <p><strong>Monto:</strong> {formatCurrency(reserva.monto_total)}</p>
                    <p><strong>ID:</strong> {reserva.id}</p>
                  </div>
                  {reserva.error && (
                    <div className="mt-2 p-2 bg-red-100 rounded text-sm text-red-700">
                      <strong>Error:</strong> {reserva.error}
                    </div>
                  )}
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};