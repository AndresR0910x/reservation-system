import React, { useState } from 'react';
import { Send, Loader2, User, Plane, Building, DollarSign } from 'lucide-react';
import { ReservaFormData } from '../types';

interface ReservaFormProps {
  onSubmit: (data: ReservaFormData) => Promise<void>;
  loading: boolean;
}

export const ReservaForm: React.FC<ReservaFormProps> = ({ onSubmit, loading }) => {
  const [formData, setFormData] = useState<ReservaFormData>({
    cliente: '',
    vuelo_destino: '',
    hotel_nombre: '',
    monto_total: 0
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(formData);
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'monto_total' ? parseFloat(value) || 0 : value
    }));
  };

  return (
    <div className="max-w-2xl mx-auto">
      <div className="bg-white rounded-xl shadow-lg p-8">
        <div className="text-center mb-8">
          <h2 className="text-3xl font-bold text-gray-900 mb-2">Generar Reserva</h2>
          <p className="text-gray-600">Ingresa los datos para crear una nueva reserva de viaje</p>
        </div>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label htmlFor="cliente" className="block text-sm font-medium text-gray-700 mb-2">
                <User className="w-4 h-4 inline mr-2" />
                Cliente
              </label>
              <input
                type="text"
                id="cliente"
                name="cliente"
                value={formData.cliente}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
                placeholder="Ana Torres"
              />
            </div>

            <div>
              <label htmlFor="vuelo_destino" className="block text-sm font-medium text-gray-700 mb-2">
                <Plane className="w-4 h-4 inline mr-2" />
                Destino del Vuelo
              </label>
              <input
                type="text"
                id="vuelo_destino"
                name="vuelo_destino"
                value={formData.vuelo_destino}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
                placeholder="Madrid"
              />
            </div>

            <div>
              <label htmlFor="hotel_nombre" className="block text-sm font-medium text-gray-700 mb-2">
                <Building className="w-4 h-4 inline mr-2" />
                Hotel
              </label>
              <input
                type="text"
                id="hotel_nombre"
                name="hotel_nombre"
                value={formData.hotel_nombre}
                onChange={handleChange}
                required
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
                placeholder="Hotel Central"
              />
            </div>

            <div>
              <label htmlFor="monto_total" className="block text-sm font-medium text-gray-700 mb-2">
                <DollarSign className="w-4 h-4 inline mr-2" />
                Monto Total
              </label>
              <input
                type="number"
                id="monto_total"
                name="monto_total"
                value={formData.monto_total}
                onChange={handleChange}
                required
                min="0"
                step="0.01"
                className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-colors"
                placeholder="900.00"
              />
            </div>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 text-white py-3 px-6 rounded-lg font-medium hover:bg-blue-700 disabled:bg-gray-400 disabled:cursor-not-allowed transition-colors flex items-center justify-center"
          >
            {loading ? (
              <>
                <Loader2 className="w-5 h-5 mr-2 animate-spin" />
                Procesando...
              </>
            ) : (
              <>
                <Send className="w-5 h-5 mr-2" />
                Crear Reserva
              </>
            )}
          </button>
        </form>
      </div>
    </div>
  );
};