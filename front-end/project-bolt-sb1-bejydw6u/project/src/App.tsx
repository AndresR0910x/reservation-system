import React, { useState } from 'react';
import { Navigation } from './components/Navigation';
import { ReservaForm } from './components/ReservaForm';
import { Dashboard } from './components/Dashboard';
import { Toast } from './components/Toast';
import { Reserva, ReservaFormData } from './types';

function App() {
  const [currentView, setCurrentView] = useState<'form' | 'dashboard'>('form');
  const [reservas, setReservas] = useState<Reserva[]>([]);
  const [loading, setLoading] = useState(false);
  const [toast, setToast] = useState<{ message: string; type: 'success' | 'error' } | null>(null);

  const handleSubmitReserva = async (data: ReservaFormData) => {
    setLoading(true);
    const reserva: Reserva = {
      id: crypto.randomUUID(),
      ...data,
      estado: 'exitosa',
      fecha_creacion: new Date().toISOString(),
    };

    try {
      const response = await fetch('http://localhost:8081/booking', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
      });

      let result;
      const text = await response.text(); // Leer el cuerpo como texto primero
      const contentType = response.headers.get('Content-Type')?.toLowerCase();

      // Intentar parsear como JSON solo si es válido
      try {
        result = text ? JSON.parse(text) : { status: 'error', message: 'No data received' };
      } catch (e) {
        // Si falla el parseo, usar el texto como mensaje
        result = {
          status: response.ok ? 'success' : 'error',
          message: text || 'No message provided by the server',
        };
      }

      if (response.ok) {
        setReservas((prev) => [{ ...reserva, message: result.message }, ...prev]);
        setToast({ message: 'Reserva creada exitosamente', type: 'success' });
        setCurrentView('dashboard');
      } else {
        setReservas((prev) => [
          { ...reserva, estado: 'fallida', error: result.message },
          ...prev,
        ]);
        setToast({
          message: `Error al crear la reserva: ${result.message}`,
          type: 'error',
        });
      }
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Error desconocido';
      setReservas((prev) => [
        { ...reserva, estado: 'fallida', error: errorMessage },
        ...prev,
      ]);
      setToast({ message: `Error de conexión: ${errorMessage}`, type: 'error' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <Navigation currentView={currentView} onViewChange={setCurrentView} />
      
      <main className="py-8 px-4 sm:px-6 lg:px-8">
        {currentView === 'form' ? (
          <ReservaForm onSubmit={handleSubmitReserva} loading={loading} />
        ) : (
          <Dashboard reservas={reservas} />
        )}
      </main>

      {toast && (
        <Toast
          message={toast.message}
          type={toast.type}
          onClose={() => setToast(null)}
        />
      )}
    </div>
  );
}

export default App;