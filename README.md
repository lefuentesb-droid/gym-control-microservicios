# Gym Control - Microservicios

## Descripción

Gym Control es un sistema para administrar información de un gimnasio.

El proyecto fue desarrollado con Java y Spring Boot usando una arquitectura de microservicios. La idea fue separar las funciones principales del sistema en servicios distintos, para que cada uno tenga su propia responsabilidad.

También se agregó Eureka Server para registrar los microservicios y API Gateway para poder entrar a las rutas desde un solo puerto.

## Integrantes

* Leonardo Fuentes
* Martin Munizaga

## Microservicios del proyecto

## Socio Service

Responsable:

* Leonardo Fuentes

Este microservicio administra los socios del gimnasio y sus membresías.

Entidades:

* Socio
* Membresía

## Actividad Service

Responsable:

* Leonardo Fuentes

Este microservicio administra las actividades del gimnasio.

Entidades:

* Entrenador
* Clase
* Reserva

## Pago Service

Responsable:

* Martin Munizaga

Este microservicio administra los pagos de los socios.

Entidades:

* Pago
* Método de Pago

## Rutina Service

Responsable:

* Martin Munizaga

Este microservicio administra las rutinas y ejercicios.

Entidades:

* Rutina
* Ejercicio
* Rutina Ejercicio

## Servicios de apoyo

## Eureka Server

Eureka Server se usa para registrar los microservicios y revisar si están activos.

URL:

```text
http://localhost:8761
```

## API Gateway

API Gateway se usa como entrada principal del sistema.

URL:

```text
http://localhost:8080
```

Con el Gateway se pueden probar las rutas principales sin entrar directamente al puerto de cada microservicio.

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Cloud
* Spring Data JPA
* Spring Web
* WebClient
* Eureka Server
* API Gateway
* MySQL
* Flyway
* Swagger
* JUnit 5
* Mockito
* Maven
* GitHub

## Requisitos para ejecutar

Para ejecutar el proyecto de forma local se necesita:

* Java JDK 21
* Visual Studio Code o IntelliJ IDEA
* MySQL iniciado desde Laragon
* Git
* Navegador para revisar Eureka y Swagger

La configuración local usa MySQL con usuario root sin contraseña.

## Bases de datos

El proyecto utiliza bases de datos separadas por microservicio.

Bases de datos usadas:

* db_socios_gym
* db_actividades_gym
* db_pago_service_dev
* db_rutina_service_dev

Flyway crea las tablas necesarias al iniciar los microservicios.

## Orden para levantar el proyecto

Para que el sistema funcione bien, se recomienda levantar los servicios en este orden:

* Iniciar MySQL desde Laragon
* Ejecutar EurekaServerApplication
* Ejecutar SocioServiceApplication
* Ejecutar ActividadServiceApplication
* Ejecutar PagoServiceApplication
* Ejecutar RutinaServiceApplication
* Ejecutar ApiGatewayApplication

Después se puede revisar Eureka en:

```text
http://localhost:8761
```

En Eureka deberían aparecer estos servicios en estado UP:

* SOCIO-SERVICE
* ACTIVIDAD-SERVICE
* PAGO-SERVICE
* RUTINA-SERVICE
* API-GATEWAY

## Comandos de ejecución

## Eureka Server

Desde la raíz del proyecto:

```powershell
.\socio-service\mvnw.cmd -f .\eureka-server\pom.xml spring-boot:run
```

## Socio Service

```powershell
cd socio-service
.\mvnw.cmd spring-boot:run
```

## Actividad Service

```powershell
cd actividad-service
.\mvnw.cmd spring-boot:run
```

## Pago Service

```powershell
cd pago-service
.\mvnw.cmd spring-boot:run
```

## Rutina Service

```powershell
cd rutina-service
.\mvnw.cmd spring-boot:run
```

## API Gateway

Desde la raíz del proyecto:

```powershell
.\socio-service\mvnw.cmd -f .\api-gateway\pom.xml spring-boot:run
```

## Rutas principales por API Gateway

URL base:

```text
http://localhost:8080
```

## Socio Service

```text
GET    /api/v1/socios
GET    /api/v1/socios/{id}
POST   /api/v1/socios
PUT    /api/v1/socios/{id}
DELETE /api/v1/socios/{id}
GET    /api/v1/socios/estado/{estado}
```

## Membresía

```text
GET    /api/v1/membresia
GET    /api/v1/membresia/{id}
POST   /api/v1/membresia
DELETE /api/v1/membresia/{id}
GET    /api/v1/membresia/estado/{estado}
GET    /api/v1/membresia/tipo/{tipo}
GET    /api/v1/membresia/socio/{idSocio}
```

## Entrenadores

```text
GET    /api/v1/entrenadores
GET    /api/v1/entrenadores/{id}
POST   /api/v1/entrenadores
PUT    /api/v1/entrenadores/{id}
DELETE /api/v1/entrenadores/{id}
```

## Clases

```text
GET    /api/v1/clases
GET    /api/v1/clases/{id}
POST   /api/v1/clases
PUT    /api/v1/clases/{id}
DELETE /api/v1/clases/{id}
GET    /api/v1/clases/estado/{estado}
GET    /api/v1/clases/nombre/{nombre}
GET    /api/v1/clases/entrenador/{idEntrenador}
```

## Reservas

```text
GET    /api/v1/reservas
GET    /api/v1/reservas/{id}
POST   /api/v1/reservas
PUT    /api/v1/reservas/{id}
DELETE /api/v1/reservas/{id}
GET    /api/v1/reservas/socio/{socioId}
```

## Pagos

```text
GET    /api/v1/pagos
GET    /api/v1/pagos/{id}
POST   /api/v1/pagos
PUT    /api/v1/pagos/{id}
DELETE /api/v1/pagos/{id}
GET    /api/v1/pagos/socio/{idSocio}
GET    /api/v1/pagos/estado/{estado}
```

## Métodos de Pago

```text
GET    /api/v1/metodos-pago
GET    /api/v1/metodos-pago/{id}
POST   /api/v1/metodos-pago
PUT    /api/v1/metodos-pago/{id}
DELETE /api/v1/metodos-pago/{id}
GET    /api/v1/metodos-pago/nombre/{nombre}
GET    /api/v1/metodos-pago/estado/{estado}
```

## Rutinas

```text
GET    /api/v1/rutinas
GET    /api/v1/rutinas/{id}
POST   /api/v1/rutinas
PUT    /api/v1/rutinas/{id}
DELETE /api/v1/rutinas/{id}
GET    /api/v1/rutinas/estado/{estado}
GET    /api/v1/rutinas/nombre/{nombre}
GET    /api/v1/rutinas/socio/{idSocio}
GET    /api/v1/rutinas/entrenador/{idEntrenador}
```

## Ejercicios

```text
GET    /api/v1/ejercicios
GET    /api/v1/ejercicios/{id}
POST   /api/v1/ejercicios
PUT    /api/v1/ejercicios/{id}
DELETE /api/v1/ejercicios/{id}
```

## Rutina Ejercicio

```text
GET    /api/v1/rutina-ejercicios
GET    /api/v1/rutina-ejercicios/{id}
POST   /api/v1/rutina-ejercicios
PUT    /api/v1/rutina-ejercicios/{id}
DELETE /api/v1/rutina-ejercicios/{id}
```

## Swagger

El proyecto usa Swagger para revisar y probar los endpoints.

## Socio Service

```text
http://localhost:8081/doc/swagger-ui/index.html
```

## Actividad Service

```text
http://localhost:8082/doc/swagger-ui/index.html
```

## Pago Service y Rutina Service

Pago Service y Rutina Service quedaron disponibles en el Swagger unificado desde el Gateway:

```text
http://localhost:8080/swagger-ui/index.html
```

Desde ahí se pueden seleccionar:

* Microservicio Pago
* Microservicio Rutina

## Comunicación entre microservicios

En el proyecto se usó WebClient para comunicar microservicios.

Un ejemplo está en Actividad Service, cuando se crea una reserva.

Para crear una reserva se necesita validar que el socio exista. Como el socio pertenece a Socio Service, no se guarda el objeto completo dentro de Actividad Service.

En Actividad Service se guarda solo el socioId y antes de registrar la reserva se consulta a Socio Service usando WebClient.

Esto permite que los microservicios estén separados, pero igual puedan comunicarse.

## Pruebas unitarias

Las pruebas unitarias están en:

```text
src/test/java
```

Se usó JUnit 5 y Mockito.

Las pruebas se hicieron principalmente en la capa Service, para probar la lógica sin depender directamente de la base de datos real.

## Pruebas de Socio Service

* SocioServiceTest
* MembresiaServiceTest

## Pruebas de Actividad Service

* EntrenadorServiceTest
* ClaseServiceTest
* ReservaServiceTest

## Pruebas de Pago Service y Rutina Service

* También se incluyen pruebas unitarias para Pago Service
* También se incluyen pruebas unitarias para Rutina Service

## Ejecutar pruebas

## Socio Service

```powershell
cd socio-service
.\mvnw.cmd clean test
```

## Actividad Service

```powershell
cd actividad-service
.\mvnw.cmd clean test
```

## Pago Service

```powershell
cd pago-service
.\mvnw.cmd clean test
```

## Rutina Service

```powershell
cd rutina-service
.\mvnw.cmd clean test
```

Si todo está correcto, Maven muestra:

```text
BUILD SUCCESS
```

## Configuración YAML

Cada microservicio tiene su archivo application.yml.

En estos archivos se configuran:

* Nombre del microservicio
* Puerto de ejecución
* Conexión a MySQL
* Configuración de Flyway
* Configuración de Swagger
* Registro en Eureka
* Rutas del API Gateway

Ejemplo:

```yaml
spring:
  application:
    name: socio-service

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## Ejecucion con Docker

El proyecto tambien se puede ejecutar con Docker siguiendo la guia del profesor.

Para esta version se dockerizaron los servicios:

* Eureka Server
* API Gateway
* Pago Service
* Rutina Service
* MariaDB

La base de datos se levanta dentro de Docker, por lo que no es necesario iniciar MySQL desde Laragon mientras se ejecuta con `docker compose`.

Comandos principales desde la raiz del proyecto:

```powershell
docker compose build --no-cache
```

```powershell
docker compose up
```

```powershell
docker compose down
```

URLs de verificacion:

```text
http://localhost:8761
http://localhost:8080/swagger-ui/index.html
```

Rutas principales probadas por Gateway:

```text
GET http://localhost:8080/api/v1/pagos
GET http://localhost:8080/api/v1/metodos-pago
GET http://localhost:8080/api/v1/rutinas
GET http://localhost:8080/api/v1/ejercicios
GET http://localhost:8080/api/v1/rutina-ejercicios
```

## Control de versiones

El proyecto se trabajó usando GitHub.

Se usaron ramas para separar el avance:

* main
* Leo
* Salvador
* integracion

La rama integracion contiene el proyecto unido con los microservicios de ambos integrantes, Eureka Server y API Gateway.

## Despliegue

El proyecto fue probado de forma local.

Se verificó que los microservicios se registran en Eureka y que las rutas principales responden desde API Gateway.

El despliegue remoto queda sujeto al alcance indicado por el profesor.

## Estado final del proyecto

El proyecto quedó integrado y probado localmente.

Estado general:

* Eureka Server funcionando
* API Gateway funcionando
* Socio Service funcionando
* Actividad Service funcionando
* Pago Service funcionando
* Rutina Service funcionando
* Rutas principales funcionando por Gateway
* Swagger disponible
* Pruebas unitarias ejecutadas correctamente
