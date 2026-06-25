# Gym Control - Arquitectura de Microservicios

## Descripción

Gym Control es un sistema de gestión para gimnasio desarrollado con Java, Spring Boot y Spring Cloud. El proyecto separa las funciones del negocio en microservicios independientes y utiliza Eureka Server para el descubrimiento de servicios y API Gateway como punto de entrada centralizado.

## Integrantes

- Leonardo Fuentes
- Martin Munizaga

## Microservicios y entidades

### Pago Service

Entidades administradas:

- Pago
- Método de Pago

### Rutina Service

Entidades administradas:

- Rutina
- Ejercicio
- Rutina Ejercicio

### Socio Service

Entidades administradas por el integrante responsable:

- Socio
- Membresía

### Actividad Service

Entidades administradas por el integrante responsable:

- Entrenador
- Clase
- Reserva

### Servicios de infraestructura

- Eureka Server: registra y permite descubrir los microservicios.
- API Gateway: centraliza el acceso y distribuye las solicitudes.

## Tecnologías utilizadas

- Java 21
- Spring Boot 4.0.6
- Spring Cloud
- Spring Data JPA
- Spring Cloud Netflix Eureka
- Spring Cloud Gateway Server Web MVC
- MySQL
- Flyway
- Swagger/OpenAPI 3.0.0
- JUnit 5
- Mockito
- Maven

## Requisitos para la ejecución

- Java JDK 21
- Visual Studio Code con las extensiones de Java y Spring Boot
- MySQL iniciado desde Laragon
- Puerto `8761` disponible para Eureka Server
- Puerto `8080` disponible para API Gateway

La configuración de desarrollo utiliza el usuario `root` de MySQL sin contraseña.

## Bases de datos

Los perfiles de desarrollo crean automáticamente las siguientes bases de datos:

- `db_pago_service_dev`
- `db_rutina_service_dev`

Flyway crea las tablas correspondientes al iniciar cada microservicio.

## Orden de ejecución local

1. Iniciar MySQL desde Laragon.
2. Ejecutar `EurekaServerApplication`.
3. Ejecutar `PagoServiceApplication`.
4. Ejecutar `RutinaServiceApplication`.
5. Ejecutar `ApiGatewayApplication`.
6. Abrir Eureka y verificar que los servicios aparezcan en estado `UP`.

Eureka Server:

`http://localhost:8761`

Los microservicios Pago y Rutina utilizan el puerto `0`, por lo que reciben un puerto dinámico y se registran automáticamente en Eureka.

## API Gateway

URL principal:

`http://localhost:8080`

Rutas de Pago Service:

- `http://localhost:8080/api/v1/pagos`
- `http://localhost:8080/api/v1/metodos-pago`

Rutas de Rutina Service:

- `http://localhost:8080/api/v1/rutinas`
- `http://localhost:8080/api/v1/ejercicios`
- `http://localhost:8080/api/v1/rutina-ejercicios`

## Swagger unificado

La documentación de Pago Service y Rutina Service se encuentra centralizada en:

`http://localhost:8080/swagger-ui/index.html`

En la parte superior de Swagger se puede seleccionar:

- Microservicio Pago
- Microservicio Rutina

## Endpoints principales

### Pagos

- `GET /api/v1/pagos`
- `GET /api/v1/pagos/{id}`
- `POST /api/v1/pagos`
- `PUT /api/v1/pagos/{id}`
- `DELETE /api/v1/pagos/{id}`
- `GET /api/v1/pagos/socio/{idSocio}`
- `GET /api/v1/pagos/estado/{estado}`

### Métodos de pago

- `GET /api/v1/metodos-pago`
- `GET /api/v1/metodos-pago/{id}`
- `POST /api/v1/metodos-pago`
- `PUT /api/v1/metodos-pago/{id}`
- `DELETE /api/v1/metodos-pago/{id}`
- `GET /api/v1/metodos-pago/nombre/{nombre}`
- `GET /api/v1/metodos-pago/estado/{estado}`

### Rutinas

- `GET /api/v1/rutinas`
- `GET /api/v1/rutinas/{id}`
- `POST /api/v1/rutinas`
- `PUT /api/v1/rutinas/{id}`
- `DELETE /api/v1/rutinas/{id}`
- `GET /api/v1/rutinas/estado/{estado}`
- `GET /api/v1/rutinas/nombre/{nombre}`
- `GET /api/v1/rutinas/socio/{idSocio}`
- `GET /api/v1/rutinas/entrenador/{idEntrenador}`

### Ejercicios

- `GET /api/v1/ejercicios`
- `GET /api/v1/ejercicios/{id}`
- `POST /api/v1/ejercicios`
- `PUT /api/v1/ejercicios/{id}`
- `DELETE /api/v1/ejercicios/{id}`

### Rutina Ejercicio

- `GET /api/v1/rutina-ejercicios`
- `GET /api/v1/rutina-ejercicios/{id}`
- `POST /api/v1/rutina-ejercicios`
- `PUT /api/v1/rutina-ejercicios/{id}`
- `DELETE /api/v1/rutina-ejercicios/{id}`

## Pruebas unitarias

Las pruebas se encuentran organizadas en `src/test/java` y utilizan JUnit 5 y Mockito.

Para ejecutarlas desde Visual Studio Code:

1. Abrir la sección `Testing`.
2. Seleccionar Pago Service o Rutina Service.
3. Presionar `Run Tests`.

Las pruebas implementadas validan operaciones de búsqueda, creación, actualización, eliminación y reglas de negocio a nivel de servicio.

## Perfiles YAML

Pago Service y Rutina Service incluyen los perfiles:

- `dev`: desarrollo local.
- `test`: ejecución de pruebas.
- `prod`: producción.

El perfil activo actualmente es `dev`.

## Estado de la comunicación entre microservicios

Pago Service y Rutina Service almacenan los identificadores externos de Socio, Membresía y Entrenador. La validación mediante WebClient se completará cuando los endpoints definitivos de los microservicios desarrollados por el otro integrante estén disponibles.

## Contenido de esta rama

Esta rama contiene el trabajo correspondiente a:

- Pago Service
- Rutina Service
- Eureka Server
- API Gateway
- Swagger unificado para Pago Service y Rutina Service
- Pruebas unitarias de Pago Service y Rutina Service

Socio Service y Actividad Service serán completados e integrados por el integrante responsable antes de llevar el proyecto a la rama principal.

## Despliegue

Actualmente el ecosistema se encuentra configurado y comprobado para ejecución local. El despliegue remoto se definirá de acuerdo con el alcance indicado por el docente.
