# ✈️ Reservation System - Microservicios para Reservas de Viajes y Hoteles

Este proyecto es un sistema de reservas distribuido que permite a los usuarios reservar vuelos, hoteles y realizar pagos, utilizando una arquitectura basada en microservicios con Java + Spring Boot, PostgreSQL y MuleSoft para la orquestación.

---

## 🧱 Arquitectura General

![Arquitectura](https://github.com/tu-usuario/tu-repo/blob/main/docs/arquitectura.png) <!-- Puedes subir un diagrama aquí -->

- 🔗 **API Gateway:** Enrutamiento dinámico entre servicios.
- 🛫 **Aeroline-Service:** Gestión de aerolíneas y vuelos.
- 🏨 **Hotel-Service:** Gestión de hoteles y disponibilidad.
- 📦 **Booking-Service:** Reserva de vuelos y hoteles.
- 💰 **Pago-Service:** Procesamiento de pagos.
- 👤 **Cliente-Service:** Gestión de usuarios/clientes.
- 🔄 **Orquestador MuleSoft:** Coordinación de servicios de forma centralizada.

---

## 🚀 Tecnologías utilizadas

| Tecnología     | Descripción                                 |
|----------------|---------------------------------------------|
| ☕ Java 21      | Lenguaje principal del backend              |
| 🌱 Spring Boot | Framework para los microservicios           |
| 🐘 PostgreSQL  | Base de datos relacional por microservicio  |
| 🧭 Eureka       | Descubrimiento de servicios                 |
| 🚪 API Gateway | Enrutador dinámico para microservicios      |
| 🔄 MuleSoft    | Orquestación de servicios                   |


---

## 📦 Cómo ejecutar el proyecto

### Requisitos previos

- Java 21
- Maven
- PostgreSQL
- MuleSoft (Anypoint Studio)

### Paso 1: Clonar el repositorio

```bash
git clone https://github.com/AndresR0910x/reservation-system.git
cd reservation-system
