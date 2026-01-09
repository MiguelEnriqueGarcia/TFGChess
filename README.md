# TFGChess Online – Java Desktop Application

Aplicación de **ajedrez online en tiempo real**, desarrollada en **Java**, con arquitectura **cliente-servidor**, múltiples partidas simultáneas y un motor de ajedrez propio completamente funcional.

Este proyecto nace como Trabajo de Fin de Grado (DAM), con un fuerte enfoque en **backend, diseño de software y comunicación en red**, inspirado en plataformas como *chess.com*.

---

## ¿Qué resuelve este proyecto?

- Jugar partidas de ajedrez **online** entre distintos usuarios
- Gestión de usuarios, amigos y salas
- Validación **segura y centralizada** de todas las jugadas
- Comunicación en tiempo real mediante **sockets**
- Separación clara entre **lógica de negocio**, **servidor** y **cliente**

---

## Por qué este proyecto es relevante

Este proyecto demuestra capacidad para:

- Diseñar una **librería de ajedrez completa desde cero**
- Implementar una **arquitectura cliente-servidor robusta**
- Trabajar con **concurrencia, hilos y sockets**
- Aplicar **patrones de diseño** en un proyecto real
- Manejar **bases de datos**, seguridad y serialización
- Mantener el código **modular, reutilizable y escalable**

---

## Funcionalidades clave

### Ajedrez
- Movimientos válidos e inválidos
- Jaque y jaque mate
- Tablas y rendición
- Enroque
- Captura al paso
- Promoción de peones
- Varias partidas simultáneas

### Sistema online
- Registro e inicio de sesión
- Gestión de amigos
- Solicitudes de amistad
- Chat privado y chat en partida
- Creación y unión a salas públicas o privadas
- Sincronización del tablero en tiempo real

---

## Arquitectura del proyecto

El proyecto está dividido en **tres grandes bloques**:

### Librería de Ajedrez
- Motor completamente independiente y reutilizable
- Validación de reglas y movimientos
- Control de estado de partida
- Serialización a JSON para persistencia o red

Puede usarse en **otros proyectos sin depender del cliente o servidor**

---

### Servidor
- Gestión de conexiones concurrentes
- Validación de jugadas (anti-cheat)
- Gestión de partidas y usuarios
- Comunicación mediante sockets
- Base de datos para usuarios y relaciones

---

### Cliente
- Aplicación de escritorio
- Interfaz gráfica con Java Swing
- Gestión de vistas y navegación
- Comunicación con el servidor en tiempo real

---

## Stack tecnológico

### Lenguaje
- **Java**

### Backend
- Sockets TCP
- Hibernate ORM
- MySQL
- Jackson (JSON)
- Spring Security (cifrado de contraseñas)
- Programación concurrente (Threads)

### Frontend
- Java Swing
- Java AWT
- FlatLaf (temas visuales)

### Otros
- Git & GitHub
- Arquitectura modular
- Patrón Command
- Patrón Factory
- Fachada (Facade)

---

## Patrones y conceptos aplicados

- Cliente / Servidor
- Fachada
- Command
- Factory
- Encapsulación
- Separación de responsabilidades
- Programación orientada a objetos
- Comunicación asíncrona
- Serialización de estado

---

## Instalación y ejecución

 - Se debe instalar o clonar el repositorio
 - Para que la base de datos del servidor se genere correctamente se debe modificar el archivo **"TFGChess\ModuloCommons\src\main\resources\hibernate.cfg.xml"**, y se debe modificar el campo hbm2ddl.auto para que tenga el valor **"create"**:
     - <property name="hbm2ddl.auto">create</property>
 - Después se debe lanzar el servidor, ejecutando el archivo: **"TFGChess\ModuloCommons\src\main\java\com\server\Server.java"**
 - Una vez hecho esto, se debería haber creado la base de datos se puede volver a poner el antiguo archivo como estaba, con el campo en **"update"**
   - <property name="hbm2ddl.auto">update</property>
 - Por último, mientras el servidor esté corriendo, debemos ejecutar el cliente.

---

## Qué se puede aprender de este proyecto

- Cómo diseñar un **motor de juego complejo**
- Cómo validar lógica crítica en el servidor
- Cómo estructurar un proyecto grande en Java
- Cómo manejar múltiples clientes simultáneos
- Cómo diseñar protocolos de comunicación propios
- Cómo aplicar patrones de diseño en un caso real

---

## Estado del proyecto

Funcional  
Probado con múltiples clientes  
Preparado para ampliaciones futuras (IA, ranking, partidas guardadas, etc.)

---

## Autor: Miguel Enrique García García

Proyecto desarrollado como **Trabajo de Fin de Grado (DAM)**  
Enfocado a demostrar competencias reales en **backend, arquitectura y Java**.
