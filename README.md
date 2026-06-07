# Spring Boot Todo Task API

API REST desarrollada con Spring Boot para la gestión de tareas (Todo App) con autenticación JWT, autorización por roles y documentación Swagger/OpenAPI.

---

## 🚀 Tecnologías utilizadas

- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT (JSON Web Token)
- Swagger / OpenAPI
- Maven

---

## 📋 Funcionalidades

### Autenticación

- Registro de usuarios
- Login con usuario y contraseña
- Generación de JWT
- Validación automática de tokens
- Contraseñas cifradas con BCrypt

### Gestión de tareas

- Crear tareas
- Obtener tareas
- Obtener tarea por ID
- Actualizar tareas (PUT)
- Actualización parcial (PATCH)
- Eliminar tareas
- Filtrado por estado
- Búsqueda por título

### Seguridad

- JWT Authentication Filter
- Endpoints públicos y privados
- Roles de usuario
- Asociación Usuario → Tareas

### Documentación

- Swagger UI
- OpenAPI 3

---

## 📂 Estructura del proyecto

```text
src/main/java/com/sergionietolabian/springbootapi

├── controller
├── dto
├── entity
├── enums
├── exception
├── mapper
├── repository
├── security
├── service
└── swagger
```

---

## 🗄️ Modelo de datos

### User

```java
User
├── id
├── username
├── password
├── role
└── tasks
```

### Task

```java
Task
├── id
├── title
├── description
├── status
└── user
```

Relación:

```text
User (1) -------- (*) Task
```

---

## 🔐 Roles

### USER

Puede:

- Ver sus tareas
- Crear sus tareas
- Modificar sus tareas
- Eliminar sus tareas

### ADMIN

Puede:

- Ver todas las tareas
- Gestionar usuarios
- Gestionar todas las tareas

---

## 🔑 Autenticación JWT

### Login

```http
POST /auth/login
```

Request:

```json
{
  "username": "sergio",
  "password": "1234"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## 📌 Endpoints principales

### Auth

| Método | Endpoint |
|----------|----------|
| POST | /auth/register |
| POST | /auth/login |

---

### User

| Método | Endpoint |
|----------|----------|
| GET | /users/me |

---

### Tasks

| Método | Endpoint |
|----------|----------|
| GET | /tasks/me |
| GET | /tasks/me/{id} |
| POST | /tasks |
| PUT | /tasks/{id} |
| PATCH | /tasks/{id} |
| DELETE | /tasks/{id} |

---

### Admin

| Método | Endpoint |
|----------|----------|
| GET | /admin/tasks |
| GET | /admin/tasks/{id} |
| GET | /admin/tasks/status |
| GET | /admin/tasks/search |

---

## ⚙️ Configuración

### application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=root
spring.datasource.password=password

jwt.secret=TU_CLAVE_SECRETA_MUY_LARGA
```

---

## 🗄️ Base de datos

### Tabla users

```sql
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);
```

### Tabla tasks

```sql
CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    user_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_tasks_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);
```

---

## 📖 Swagger

Disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Próximas mejoras

- [x] JWT Authentication
- [x] Swagger/OpenAPI
- [x] DTO Pattern
- [x] Task Mapper
- [x] User ↔ Task Relationship
- [ ] Refresh Tokens
- [ ] Global Exception Handler
- [ ] Pagination
- [ ] Sorting
- [ ] Docker
- [ ] Unit Tests
- [ ] Integration Tests
- [ ] Angular Frontend
- [ ] CI/CD con GitHub Actions
- [ ] Deploy en AWS

---

## 👨‍💻 Autor

**Sergio Nieto Labián**

GitHub:

https://github.com/sergionietolabian
