Documentación de los Microservicios de Reserva de Viajes
A continuación se presenta la documentación actualizada y formateada de los microservicios que componen el sistema de reservas de viajes. Esta documentación incluye la descripción de los servicios, sus endpoints, modelos de datos, configuración del API Gateway, un diagrama de secuencia, un ejemplo de flujo completo con ejecución de rutas, y las instrucciones para levantar los servicios y probar las APIs.
Tabla de Contenidos

Configuración del API Gateway
Servicio de Clientes
Servicio de Aerolíneas y Vuelos
Servicio de Hoteles
Servicio de Reservas
Servicio de Pagos
Levantamiento de los Servicios
Pruebas de las APIs
Diagrama de Secuencia
Ejemplo de Flujo Completo


1. Configuración del API Gateway
El sistema utiliza un API Gateway configurado en el puerto 9090 para enrutar solicitudes a los microservicios correspondientes. A continuación, se muestra la configuración en formato YAML:
server:
  port: 9090

spring:
  application:
    name: API-GATEWAY
  cloud:
    gateway:
      routes:
        - id: CLIENTE-SERVICE
          uri: lb://CLIENTE-SERVICE
          predicates:
            - Path=/api/clients/**
        - id: AEROLINE-SERVICE
          uri: lb://AEROLINE-SERVICE
          predicates:
            - Path=/api/airlines/**
        - id: BOOKING-SERVICE
          uri: lb://BOOKING-SERVICE
          predicates:
            - Path=/api/reservations/**
        - id: HOTEL-SERVICE
          uri: lb://HOTEL-SERVICE
          predicates:
            - Path=/api/hotels/**
        - id: PAGO-SERVICE
          uri: lb://PAGO-SERVICE
          predicates:
            - Path=/api/payments/**
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    hostname: localhost


2. Servicio de Clientes
Descripción: Gestiona la información de los clientes, incluyendo sus datos personales y saldos disponibles.Base de datos: PostgreSQL (travel_client_db)
Endpoints



Método
Endpoint
Descripción



POST
/api/clients
Crear un nuevo cliente


GET
/api/clients
Obtener todos los clientes


GET
/api/clients/{id}
Obtener un cliente por ID


GET
/api/clients/{id}/balance
Obtener el saldo de un cliente


PUT
/api/clients/{id}/balance
Actualizar el saldo de un cliente


POST
/api/clients/{id}/deactivate
Desactivar un cliente


POST
/api/clients/{id}/activate
Activar un cliente


Modelos de Datos
ClientDTO
{
  "id": 1,
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@example.com",
  "phoneNumber": "+593987654321",
  "address": "Av. Amazonas N23-45, Quito",
  "balance": 1000.00,
  "active": true
}

ClientCreateDTO
{
  "firstName": "Juan",
  "lastName": "Pérez",
  "email": "juan.perez@example.com",
  "phoneNumber": "+593987654321",
  "address": "Av. Amazonas N23-45, Quito",
  "initialBalance": 1000.00
}


3. Servicio de Aerolíneas y Vuelos
Descripción: Gestiona la información de aerolíneas y vuelos disponibles.Base de datos: PostgreSQL (travel_flight_db)
Endpoints
Aerolíneas



Método
Endpoint
Descripción



POST
/api/airlines
Crear una nueva aerolínea


GET
/api/airlines
Obtener todas las aerolíneas


GET
/api/airlines/{id}
Obtener una aerolínea por ID


PUT
/api/airlines/{id}
Actualizar una aerolínea


DELETE
/api/airlines/{id}
Eliminar una aerolínea


Vuelos



Método
Endpoint
Descripción



POST
/api/flights
Crear un nuevo vuelo


GET
/api/flights
Obtener todos los vuelos


GET
/api/flights/{id}
Obtener un vuelo por ID


GET
/api/flights/available
Obtener vuelos disponibles (params: origin, destination)


PUT
/api/flights/{id}
Actualizar un vuelo


DELETE
/api/flights/{id}
Eliminar un vuelo


Modelos de Datos
FlightDTO
{
  "id": 1,
  "aerolineaId": 1,
  "origen": "Quito",
  "destino": "Guayaquil",
  "fechaSalida": "2025-08-01T08:00:00",
  "fechaLlegada": "2025-08-01T10:00:00",
  "precio": 180.00,
  "disponibilidad": true,
  "asientosDisponibles": 150
}

AirlineDTO
{
  "id": 1,
  "nombre": "Aerolíneas Ejemplo",
  "codigo": "AE"
}


4. Servicio de Hoteles
Descripción: Gestiona la información de hoteles disponibles.Base de datos: PostgreSQL (travel_hotel_db)
Endpoints



Método
Endpoint
Descripción



POST
/api/hotels
Crear un nuevo hotel


GET
/api/hotels
Obtener todos los hoteles


GET
/api/hotels/{id}
Obtener un hotel por ID


GET
/api/hotels/available
Obtener hoteles disponibles (param: destination)


PUT
/api/hotels/{id}
Actualizar un hotel


DELETE
/api/hotels/{id}
Eliminar un hotel


Modelo de Datos
HotelDTO
{
  "id": 1,
  "nombre": "Hotel Plaza Grande",
  "ubicacion": "Quito",
  "cuartosDisponibles": 50,
  "precioPorNoche": 120.00,
  "disponibilidad": true
}


5. Servicio de Reservas
Descripción: Gestiona las reservas de viajes, integrando vuelos y hoteles.Base de datos: PostgreSQL (travel_reservation_db)
Endpoints



Método
Endpoint
Descripción



POST
/api/reservations
Crear una nueva reserva


GET
/api/reservations
Obtener todas las reservas


GET
/api/reservations/{id}
Obtener una reserva por ID


Modelos de Datos
ReservationRequestDTO
{
  "clientId": 1,
  "origin": "Quito",
  "destination": "Guayaquil",
  "flightId": 1,
  "hotelId": 1,
  "seatsNumber": 2
}

ReservationResponseDTO
{
  "id": 1,
  "clientId": 1,
  "origin": "Quito",
  "destination": "Guayaquil",
  "flightId": 1,
  "hotelId": 1,
  "seatsNumber": 2,
  "totalPrice": 420.00,
  "reservationDate": "2025-07-15T14:30:00",
  "status": "CONFIRMED"
}


6. Servicio de Pagos
Descripción: Gestiona los pagos de las reservas y actualiza los saldos de los clientes.Base de datos: PostgreSQL (travel_payment_db)
Endpoints



Método
Endpoint
Descripción



POST
/api/payments
Procesar un nuevo pago


GET
/api/payments/{id}
Obtener un pago por ID


GET
/api/payments/transaction/{transactionId}
Obtener un pago por ID de transacción


POST
/api/payments/{id}/refund
Procesar un reembolso


Modelos de Datos
PaymentRequestDTO
{
  "reservationId": 1,
  "clientId": 1,
  "amount": 420.00,
  "paymentMethod": "WALLET"
}

PaymentResponseDTO
{
  "id": 1,
  "reservationId": 1,
  "clientId": 1,
  "amount": 420.00,
  "status": "COMPLETED",
  "paymentDate": "2025-07-15T14:35:00",
  "paymentMethod": "WALLET",
  "transactionId": "TXN-ABC123"
}


7. Levantamiento de los Servicios
Para levantar los microservicios, se requiere configurar el entorno, incluyendo las bases de datos y el servidor Eureka para el descubrimiento de servicios. A continuación, se detallan los pasos:
Prerrequisitos

Java 17 o superior.
Maven para la gestión de dependencias.
PostgreSQL con las bases de datos creadas: travel_client_db, travel_flight_db, travel_hotel_db, travel_reservation_db, travel_payment_db.
Docker (opcional, para contenedores).
Herramientas de prueba: Postman, cURL o cualquier cliente HTTP.

Pasos para Levantar los Servicios

Configurar el Servidor Eureka:

Clona el repositorio del servidor Eureka (o crea uno si no existe).
Configura el archivo application.yml:server:
  port: 8761
eureka:
  client:
    register-with-eureka: false
    fetch-registry: false


Ejecuta el servidor:mvn spring-boot:run


Verifica que el servidor esté activo en http://localhost:8761.


Configurar las Bases de Datos:

Crea las bases de datos en PostgreSQL:CREATE DATABASE travel_client_db;
CREATE DATABASE travel_flight_db;
CREATE DATABASE travel_hotel_db;
CREATE DATABASE travel_reservation_db;
CREATE DATABASE travel_payment_db;


Configura las conexiones en los archivos application.yml de cada microservicio. Ejemplo para el servicio de clientes:spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/travel_client_db
    username: postgres





 bup: 5432         password: your_password       jpa:         hibernate:           ddl-auto: update     ```

Levantar los Microservicios:

Clona los repositorios de los microservicios (CLIENTE-SERVICE, AEROLINE-SERVICE, HOTEL-SERVICE, BOOKING-SERVICE, PAGO-SERVICE).
Para cada microservicio, ejecuta:mvn spring-boot:run


Alternativamente, usa Docker si los servicios están contenedorizados:docker-compose up


Verifica que cada servicio se registre en Eureka (http://localhost:8761).


Levantar el API Gateway:

Clona el repositorio del API Gateway.
Configura el archivo application.yml como se mostró en la sección 1.
Ejecuta:mvn spring-boot:run


Verifica que el API Gateway esté activo en http://localhost:9090.



Notas

Asegúrate de que las bases de datos estén inicializadas con los esquemas correspondientes antes de levantar los servicios.
Si usas Docker, crea un archivo docker-compose.yml que incluya los servicios, PostgreSQL y Eureka.


8. Pruebas de las APIs
Para probar las APIs, puedes usar herramientas como Postman, cURL, o cualquier cliente HTTP. A continuación, se detalla cómo probar los endpoints principales de cada microservicio utilizando cURL. Todas las solicitudes se realizan a través del API Gateway en http://localhost:9090.
Pruebas del Servicio de Clientes

Crear un cliente:
curl -X POST http://localhost:9090/api/clients \
-H "Content-Type: application/json" \
-d '{
  "firstName": "María",
  "lastName": "González",
  "email": "maria.gonzalez@example.com",
  "phoneNumber": "+593987654322",
  "address": "Av. Shyris N45-67, Quito",
  "initialBalance": 1500.00
}'

Respuesta esperada:
{
  "id": 1,
  "firstName": "María",
  "lastName": "González",
  "email": "maria.gonzalez@example.com",
  "phoneNumber": "+593987654322",
  "address": "Av. Shyris N45-67, Quito",
  "balance": 1500.00,
  "active": true
}


Obtener un cliente por ID:
curl -X GET http://localhost:9090/api/clients/1

Respuesta esperada:
{
  "id": 1,
  "firstName": "María",
  "lastName": "González",
  "email": "maria.gonzalez@example.com",
  "phoneNumber": "+593987654322",
  "address": "Av. Shyris N45-67, Quito",
  "balance": 1500.00,
  "active": true
}



Pruebas del Servicio de Aerolíneas y Vuelos

Crear una aerolínea:
curl -X POST http://localhost:9090/api/airlines \
-H "Content-Type: application/json" \
-d '{
  "nombre": "Aerolíneas Ejemplo",
  "codigo": "AE"
}'

Respuesta esperada:
{
  "id": 1,
  "nombre": "Aerolíneas Ejemplo",
  "codigo": "AE"
}


Crear un vuelo:
curl -X POST http://localhost:9090/api/flights \
-H "Content-Type: application/json" \
-d '{
  "aerolineaId": 1,
  "origen": "Quito",
  "destino": "Guayaquil",
  "fechaSalida": "2025-08-01T08:00:00",
  "fechaLlegada": "2025-08-01T10:00:00",
  "precio": 180.00,
  "disponibilidad": true,
  "asientosDisponibles": 150
}'

Respuesta esperada:
{
  "id": 1,
  "aerolineaId": 1,
  "origen": "Quito",
  "destino": "Guayaquil",
  "fechaSalida": "2025-08-01T08:00:00",
  "fechaLlegada": "2025-08-01T10:00:00",
  "precio": 180.00,
  "disponibilidad": true,
  "asientosDisponibles": 150
}


Buscar vuelos disponibles:
curl -X GET "http://localhost:9090/api/flights/available?origin=Quito&destination=Guayaquil"

Respuesta esperada:
[
  {
    "id": 1,
    "aerolineaId": 1,
    "origen": "Quito",
    "destino": "Guayaquil",
    "fechaSalida": "2025-08-01T08:00:00",
    "fechaLlegada": "2025-08-01T10:00:00",
    "precio": 180.00,
    "disponibilidad": true,
    "asientosDisponibles": 150
  }
]



Pruebas del Servicio de Hoteles

Crear un hotel:
curl -X POST http://localhost:9090/api/hotels \
-H "Content-Type: application/json" \
-d '{
  "nombre": "Hotel Plaza Grande",
  "ubicacion": "Quito",
  "cuartosDisponibles": 50,
  "precioPorNoche": 120.00,
  "disponibilidad": true
}'

Respuesta esperada:
{
  "id": 1,
  "nombre": "Hotel Plaza Grande",
  "ubicacion": "Quito",
  "cuartosDisponibles": 50,
  "precioPorNoche": 120.00,
  "disponibilidad": true
}


Buscar hoteles disponibles:
curl -X GET "http://localhost:9090/api/hotels/available?destination=Guayaquil"

Respuesta esperada:
[
  {
    "id": 1,
    "nombre": "Hotel Plaza Grande",
    "ubicacion": "Quito",
    "cuartosDisponibles": 50,
    "precioPorNoche": 120.00,
    "disponibilidad": true
  }
]



Pruebas del Servicio de Reservas

Crear una reserva:
curl -X POST http://localhost:9090/api/reservations \
-H "Content-Type: application/json" \
-d '{
  "clientId": 1,
  "origin": "Quito",
  "destination": "Guayaquil",
  "flightId": 1,
  "hotelId": 1,
  "seatsNumber": 2
}'

Respuesta esperada:
{
  "id": 1,
  "clientId": 1,
  "origin": "Quito",
  "destination": "Guayaquil",
  "flightId": 1,
  "hotelId": 1,
  "seatsNumber": 2,
  "totalPrice": 420.00,
  "reservationDate": "2025-07-15T14:30:00",
  "status": "CONFIRMED"
}


Obtener una reserva:
curl -X GET http://localhost:9090/api/reservations/1

Respuesta esperada:
{
  "id": 1,
  "clientId": 1,
  "origin": "Quito",
  "destination": "Guayaquil",
  "flightId": 1,
  "hotelId": 1,
  "seatsNumber": 2,
  "totalPrice": 420.00,
  "reservationDate": "2025-07-15T14:30:00",
  "status": "CONFIRMED"
}



Pruebas del Servicio de Pagos

Procesar un pago:
curl -X POST http://localhost:9090/api/payments \
-H "Content-Type: application/json" \
-d '{
  "reservationId": 1,
  "clientId": 1,
  "amount": 420.00,
  "paymentMethod": "WALLET"
}'

Respuesta esperada:
{
  "id": 1,
  "reservationId": 1,
  "clientId": 1,
  "amount": 420.00,
  "status": "COMPLETED",
  "paymentDate": "2025-07-15T14:35:00",
  "paymentMethod": "WALLET",
  "transactionId": "TXN-ABC123"
}


Obtener un pago por ID:
curl -X GET http://localhost:9090/api/payments/1

Respuesta esperada:
{
  "id": 1,
  "reservationId": 1,
  "clientId": 1,
  "amount": 420.00,
  "status": "COMPLETED",
  "paymentDate": "2025-07-15T14:35:00",
  "paymentMethod": "WALLET",
  "transactionId": "TXN-ABC123"
}



Notas para las Pruebas

Asegúrate de que los servicios estén levantados y registrados en Eureka antes de realizar las pruebas.
Los IDs (clientId, flightId, hotelId, etc.) deben corresponder a registros existentes en las bases de datos.
Si usas Postman, importa las solicitudes cURL directamente o crea una colección para organizarlas.
Verifica las respuestas HTTP (200 OK, 400 Bad Request, etc.) para diagnosticar errores.


9. Diagrama de Secuencia
El siguiente diagrama de secuencia ilustra el flujo de interacción entre los microservicios para procesar una reserva completa:
sequenceDiagram
    participant Cliente
    participant API_Gateway
    participant Cliente_Service
    participant Aeroline_Service
    participant Hotel_Service
    participant Reservation_Service
    participant Payment_Service

    Cliente->>API_Gateway: POST /api/clients
    API_Gateway->>Cliente_Service: Crear cliente
    Cliente_Service-->>API_Gateway: Cliente creado
    API_Gateway-->>Cliente: Respuesta (ClientDTO)

    Cliente->>API_Gateway: GET /api/flights/available?origin=Quito&destination=Guayaquil
    API_Gateway->>Aeroline_Service: Buscar vuelos disponibles
    Aeroline_Service-->>API_Gateway: Lista de vuelos
    API_Gateway-->>Cliente: Lista de vuelos (FlightDTO)

    Cliente->>API_Gateway: GET /api/hotels/available?destination=Guayaquil
    API_Gateway->>Hotel_Service: Buscar hoteles disponibles
    Hotel_Service-->>API_Gateway: Lista de hoteles
    API_Gateway-->>Cliente: Lista de hoteles (HotelDTO)

    Cliente->>API_Gateway: POST /api/reservations
    API_Gateway->>Reservation_Service: Crear reserva
    Reservation_Service->>Aeroline_Service: Validar vuelo
    Reservation_Service->>Hotel_Service: Validar hotel
    Reservation_Service->>Cliente_Service: Validar cliente
    Reservation_Service-->>API_Gateway: Reserva creada
    API_Gateway-->>Cliente: Respuesta (ReservationResponseDTO)

    Cliente->>API_Gateway: POST /api/payments
    API_Gateway->>Payment_Service: Procesar pago
    Payment_Service->>Cliente_Service: Actualizar saldo
    Payment_Service-->>API_Gateway: Pago completado
    API_Gateway-->>Cliente: Respuesta (PaymentResponseDTO)

    Cliente->>API_Gateway: GET /api/reservations/1
    API_Gateway->>Reservation_Service: Obtener reserva
    Reservation_Service-->>API_Gateway: Detalles de la reserva
    API_Gateway-->>Cliente: Respuesta (ReservationResponseDTO)


10. Ejemplo de Flujo Completo
A continuación, se describe un flujo completo de una reserva de viaje utilizando las rutas del sistema. Cada solicitud incluye un ejemplo de cuerpo (si aplica) y una breve descripción del propósito.
1. Registro de Cliente
Solicitud:
curl -X POST http://localhost:9090/api/clients \
-H "Content-Type: application/json" \
-d '{
  "firstName": "María",
  "lastName": "González",
  "email": "maria.gonzalez@example.com",
  "phoneNumber": "+593987654322",
  "address": "Av. Shyris N45-67, Quito",
  "initialBalance": 1500.00
}'

Descripción: Registra un nuevo cliente con un saldo inicial de $1500.00. El servicio de clientes crea un nuevo registro en travel_client_db y devuelve un ClientDTO.
2. Búsqueda de Vuelos
Solicitud:
curl -X GET "http://localhost:9090/api/flights/available?origin=Quito&destination=Guayaquil"

Descripción: Busca vuelos disponibles entre Quito y Guayaquil. El servicio de aerolíneas responde con una lista de FlightDTO disponibles.
3. Búsqueda de Hoteles
Solicitud:
curl -X GET "http://localhost:9090/api/hotels/available?destination=Guayaquil"

Descripción: Busca hoteles disponibles en Guayaquil. El servicio de hoteles responde con una lista de HotelDTO disponibles.
4. Creación de Reserva
Solicitud:
curl -X POST http://localhost:9090/api/reservations \
-H "Content-Type: application/json" \
-d '{
  "clientId": 1,
  "origin": "Quito",
  "destination": "Guayaquil",
  "flightId": 1,
  "hotelId": 1,
  "seatsNumber": 2
}'

Descripción: Crea una reserva para un cliente, especificando el vuelo y el hotel seleccionados. El servicio de reservas valida la disponibilidad con los servicios de aerolíneas, hoteles y clientes, y devuelve un ReservationResponseDTO.
5. Procesamiento de Pago
Solicitud:
curl -X POST http://localhost:9090/api/payments \
-H "Content-Type: application/json" \
-d '{
  "reservationId": 1,
  "clientId": 1,
  "amount": 420.00,
  "paymentMethod": "WALLET"
}'

Descripción: Procesa el pago de la reserva utilizando el saldo del cliente. El servicio de pagos actualiza el saldo en el servicio de clientes y devuelve un PaymentResponseDTO.
6. Consulta de Reserva
Solicitud:
curl -X GET http://localhost:9090/api/reservations/1

Descripción: Obtiene los detalles de la reserva con ID 1. El servicio de reservas responde con un ReservationResponseDTO.

Este documento proporciona una visión integral de los microservicios de reservas de viajes, sus interacciones, instrucciones para levantarlos y probarlos, y un ejemplo práctico de uso. Para más detalles sobre la implementación, consulta los repositorios de cada microservicio.