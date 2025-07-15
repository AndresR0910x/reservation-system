import React from 'react';
import { PlusCircle, BarChart3 } from 'lucide-react';

interface NavigationProps {
  currentView: 'form' | 'dashboard';
  onViewChange: (view: 'form' | 'dashboard') => void;
}

export const Navigation: React.FC<NavigationProps> = ({ currentView, onViewChange }) => {
  return (
    <nav className="bg-white shadow-sm border-b border-gray-200">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          <div className="flex items-center">
            <h1 className="text-xl font-bold text-gray-900">Viajes 360 Orquestacion MuleSoft</h1>
          </div>
          
          <div className="flex space-x-4">
            <button
              onClick={() => onViewChange('form')}
              className={`flex items-center px-4 py-2 rounded-lg font-medium transition-colors ${
                currentView === 'form'
                  ? 'bg-blue-100 text-blue-700'
                  : 'text-gray-600 hover:text-gray-900 hover:bg-gray-100'
              }`}
            >
              <PlusCircle className="w-5 h-5 mr-2" />
              Generar Reserva
            </button>
            
            <button
              onClick={() => onViewChange('dashboard')}
              className={`flex items-center px-4 py-2 rounded-lg font-medium transition-colors ${
                currentView === 'dashboard'
                  ? 'bg-blue-100 text-blue-700'
                  : 'text-gray-600 hover:text-gray-900 hover:bg-gray-100'
              }`}
            >
              <BarChart3 className="w-5 h-5 mr-2" />
              Dashboard
            </button>
          </div>
        </div>
      </div>
    </nav>
  );
};