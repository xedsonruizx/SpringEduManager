# SpringEduManager

Proyecto Final Modulo 6 — Aplicacion Java EE + Spring Boot + MVC + JPA + REST + Security.

Aplicacion web para que la coordinacion academica de un bootcamp gestione a sus estudiantes: registro/login con roles ADMIN y USER, CRUD completo de estudiantes con validaciones (incluido un validador de RUT chileno real), y una API REST equivalente para integracion con otros sistemas.

## Tecnologias

- Java 21
- Spring Boot 4.1.1
- Spring MVC + Thymeleaf
- Spring Data JPA (Hibernate)
- Spring Security (BCrypt, roles, login/logout)
- Bean Validation (Jakarta Validation)
- MySQL
- Bootstrap 5 + Bootstrap Icons + SweetAlert2
- Maven

## Requisitos previos

- JDK 21
- Maven (o el wrapper `mvnw` incluido)
- MySQL corriendo localmente

## Base de datos

Crear el esquema (las tablas las genera Hibernate automaticamente al arrancar, con `ddl-auto=update`):

```sql
CREATE DATABASE IF NOT EXISTS springedumanager
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

Ajustar usuario y password de MySQL en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/springedumanager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

## Como ejecutar

```bash
./mvnw spring-boot:run
```

La aplicacion queda disponible en `http://localhost:8080`.

Al arrancar por primera vez (tabla `estudiantes` vacia), un `CommandLineRunner` carga 10 estudiantes de ejemplo automaticamente.

## Usuarios y roles

- La home (`/`) es publica; el resto de la aplicacion exige sesion iniciada.
- Cualquiera puede registrarse en `/registro` (login automatico tras registrarse).
- El **primer usuario que se registre en el sistema queda como ADMIN**; todos los siguientes quedan como USER.
- Permisos:
  - **ADMIN**: crear, editar y eliminar estudiantes, ademas de listar/ver/buscar.
  - **USER**: solo listar, ver detalle y buscar estudiantes.

## Modulo Estudiantes

Entidad `Estudiante` con 12 campos validados:

`rut`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `email`, `telefono`, `fechaNacimiento`, `direccion`, `ciudad`, `asistencia`, `promedio`, `activo`.

El campo `rut` usa un validador propio (`@Rut`) que verifica el digito verificador con el algoritmo modulo 11 chileno, y se normaliza automaticamente al formato `XXXXXXXX-X` antes de guardarse.

Acciones disponibles (vistas en `/estudiantes`):

| Accion | Ruta | Rol |
|---|---|---|
| Listar | `GET /estudiantes` | ADMIN, USER |
| Buscar | `GET /estudiantes/buscar?texto=` | ADMIN, USER |
| Ver detalle | `GET /estudiantes/ver/{id}` | ADMIN, USER |
| Nuevo | `GET /estudiantes/nuevo` | ADMIN |
| Editar | `GET /estudiantes/editar/{id}` | ADMIN |
| Guardar (crear/actualizar) | `POST /estudiantes/guardar` | ADMIN |
| Eliminar | `POST /estudiantes/eliminar/{id}` | ADMIN |

El filtro (`texto`) hace una busqueda parcial, sin distinguir mayusculas, sobre todas las columnas de texto que se muestran en la tabla: `rut`, `nombre`, `apellidoPaterno`, `apellidoMaterno`, `email` y `ciudad`.

Metodos de busqueda personalizados en `EstudianteRepository`:
- `buscarPorTexto(texto)` — consulta JPQL con `LIKE` sobre los 6 campos de texto.
- `findByActivoTrue()` — solo estudiantes activos.

La lista es responsiva: tabla completa en pantallas grandes, tarjetas apiladas en mobile/tablet. Las confirmaciones de eliminacion y los mensajes de exito/error usan SweetAlert2.

## API REST

Base: `/api/estudiantes`. Publica (no requiere sesion), pensada para integracion con otros sistemas.

| Metodo | Ruta | Descripcion |
|---|---|---|
| GET | `/api/estudiantes` | Lista todos |
| GET | `/api/estudiantes/{id}` | Detalle (404 si no existe) |
| GET | `/api/estudiantes/buscar?texto=` | Busqueda parcial sobre rut/nombre/apellidos/email/ciudad |
| POST | `/api/estudiantes` | Crea (201) |
| PUT | `/api/estudiantes/{id}` | Actualiza |
| DELETE | `/api/estudiantes/{id}` | Elimina (204) |

Ejemplo de creacion:

```bash
curl -X POST http://localhost:8080/api/estudiantes \
  -H "Content-Type: application/json" \
  -d '{
    "rut": "12345678-5",
    "nombre": "Ana",
    "apellidoPaterno": "Perez",
    "apellidoMaterno": "Soto",
    "email": "ana.perez@mail.com",
    "telefono": "912345678",
    "fechaNacimiento": "2000-05-14",
    "direccion": "Av. Siempre Viva 123",
    "ciudad": "Santiago",
    "asistencia": 95.0,
    "promedio": 6.2,
    "activo": true
  }'
```

### Manejo de errores de la API

Todas las respuestas de error siguen el formato `{ "success": false, "message": "..." }` (los errores de validacion incluyen ademas un objeto `errors` con el detalle por campo):

| Situacion | Codigo |
|---|---|
| Datos invalidos (`@Valid`, `@Rut`, etc.) | 400 |
| Falta un parametro obligatorio | 400 |
| Recurso no encontrado | 404 |
| RUT o email duplicado | 409 |
| Error inesperado | 500 |

## Paginas de error

- `error/403.html` — acceso denegado (rol insuficiente).
- `error/404.html` — recurso o ruta inexistente.

## Estructura del proyecto

```
src/main/java/com/iseg/
├── SpringEduManagerApplication.java
├── config/          DataLoader (seeder de estudiantes de ejemplo)
├── controller/      Controladores MVC (vistas)
├── dto/             DTOs de formularios
├── exception/       Manejo de errores (MVC y REST)
├── model/           Entidades JPA (Estudiante, Usuario, Rol)
├── repository/      Repositorios Spring Data JPA
├── rest/            Controladores REST (API)
├── security/        Configuracion de Spring Security
├── service/         Logica de negocio
├── util/            RutUtil (validacion/formateo de RUT)
└── validation/       Anotacion @Rut y su validador

src/main/resources/
├── application.properties
├── static/css/      estilos.css
└── templates/       Vistas Thymeleaf (home, login, registro, estudiantes/, error/, fragments/)
```

## Empaquetado

No es necesario generar un WAR; el proyecto se ejecuta directamente como JAR (`mvnw spring-boot:run` o `java -jar target/SpringEduManager-1.0.jar`).
