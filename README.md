# Reactive Gateway

El Gateway actúa como un punto de entrada único para todos los microservicios en la arquitectura. Se encarga de enrutar las solicitudes a los microservicios adecuados y gestionar la seguridad de las comunicaciones entre los clientes y los servicios.

## Arquitectura

El **Reactive Gateway** es parte de una arquitectura de microservicios compuesta por los siguientes componentes:

- **[Eureka Server](https://github.com/DanielRodado/EurekaServer-ToDoList)**: Registro y descubrimiento de servicios.
- **[User Service](https://github.com/DanielRodado/UserService-ToDoList)**: Servicio encargado de la gestión de usuarios.
- **[Task Service](https://github.com/DanielRodado/TaskService-ToDoList)**: Servicio encargado de la gestión de tareas.

## Tecnologías Usadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Cloud Reactive Gateway**: Biblioteca de Spring Cloud que proporciona una solución de gateway basada en API para enrutar y gestionar el tráfico de forma reactiva.
- **Spring Cloud Netflix Eureka Client**: Proporciona el soporte necesario para que un microservicio se registre y descubra otros servicios en un servidor Eureka.
- **Spring Security**: Framework para proporcionar seguridad en las aplicaciones Spring, utilizado aquí para la autenticación y autorización.
- **JWT (JSON Web Tokens)**: Método para la transmisión segura de información entre partes, utilizado para la autenticación en el Gateway.

## Descripción del Servicio

### Funcionalidades

- **Enrutamiento de Solicitudes**: El Gateway enruta las solicitudes a los microservicios apropiados basándose en las rutas configuradas.
- **Gestión de Seguridad**: Implementa seguridad para todas las solicitudes entrantes, incluyendo autenticación y autorización utilizando JWT.
- **Filtrado de Solicitudes**: Aplica filtros para manejar el tráfico y realizar tareas como autenticación y autorización antes de enrutar las solicitudes a los microservicios.

### Seguridad

El Gateway implementa **Spring Security** para gestionar la seguridad de todas las solicitudes entrantes:

- **Autenticación con JWT**: Utiliza **JWT** para autenticar las solicitudes. Los clientes deben incluir un token JWT válido en el encabezado `Authorization` en el formato `Bearer <token>`.
  
- **Configuración de Seguridad**: La seguridad es aplicada en el Gateway, donde se valida el token JWT y se asegura que solo los usuarios autorizados puedan acceder a los microservicios.

### Estructura del Proyecto

- **`src/main/java/com/example/gateway`**: Código fuente del Gateway.
  - **`SecurityConfig`**: Configuración de seguridad.
  - **`JwtAuthenticationFilter`**: Filtros personalizados aplicados a las solicitudes.
  - **`GatewayServiceApplication.java`**: Clase principal para iniciar la aplicación Gateway.

- **`src/main/resources`**: Archivos de configuración.
