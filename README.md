# Task Manager REST API

A small REST API for task management developed using Java and Spring Boot.

## 📋 Features

- ✅ **User authentication** via email and password
- ✅ **JWT authentication** with tokens
- ✅ **CRUD operations** for tasks
- ✅ **Pagination** (5 tasks per page)
- ✅ **Sorting and filtering** of tasks
- ✅ **Search** tasks by title
- ✅ **Mark tasks** as completed/incomplete
- ✅ **Task statistics** for users

## 🛠 Technology Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security** (JWT)
- **Spring Data JPA**
- **H2 Database** (in-memory)
- **Maven**
- **Jakarta Validation**

## 🚀 Quick Start

### Requirements
- Java 17 or higher
- Maven 3.6+

### Installation and Running

1. **Clone the repository:**
```bash
git clone <repository-url>
cd task-manager-api
```

2. **Build the project:**
```bash
mvn clean compile
```

3. **Run the application:**
```bash
mvn spring-boot:run
```

4. **Application will be available at:**
```
http://localhost:8080
```

### Alternative way to run:
```bash
mvn clean package
java -jar target/task-manager-api-1.0.0.jar
```

## 📖 API Documentation

### Base URL: `http://localhost:8080`

### 🔐 Authentication

#### Registration
```http
POST /api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer",
  "email": "user@example.com",
  "userId": 1
}
```

### 📝 Task Management

**Important:** All task requests require `Authorization: Bearer <token>` header

#### Get all tasks (with pagination)
```http
GET /api/tasks?page=0&size=5&sortBy=createdAt&sortDir=desc
Authorization: Bearer <token>
```

#### Create task
```http
POST /api/tasks
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Learn Spring Boot",
  "description": "Complete Spring Boot course"
}
```

#### Get task by ID
```http
GET /api/tasks/{id}
Authorization: Bearer <token>
```

#### Update task
```http
PUT /api/tasks/{id}
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "Updated title",
  "description": "Updated description",
  "completed": true
}
```

#### Mark as completed
```http
PATCH /api/tasks/{id}/complete
Authorization: Bearer <token>
```

#### Mark as incomplete
```http
PATCH /api/tasks/{id}/incomplete
Authorization: Bearer <token>
```

#### Delete task
```http
DELETE /api/tasks/{id}
Authorization: Bearer <token>
```

#### Filter by status
```http
GET /api/tasks/status?completed=true&page=0&size=5
Authorization: Bearer <token>
```

#### Search tasks
```http
GET /api/tasks/search?q=Spring&page=0&size=5
Authorization: Bearer <token>
```

#### Task statistics
```http
GET /api/tasks/statistics
Authorization: Bearer <token>
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/aufgabe/danyil/test/
│   │       ├── TaskManagerApplication.java      # Main class
│   │       ├── config/
│   │       │   └── SecurityConfig.java          # Security configuration
│   │       ├── controller/
│   │       │   ├── AuthController.java          # Authentication controller
│   │       │   ├── TaskController.java          # Task controller
│   │       │   └── TestController.java          # Test controller
│   │       ├── dto/
│   │       │   ├── request/                     # Request DTOs
│   │       │   └── response/                    # Response DTOs
│   │       ├── entity/
│   │       │   ├── User.java                    # User entity
│   │       │   └── Task.java                    # Task entity
│   │       ├── repository/
│   │       │   ├── UserRepository.java          # User repository
│   │       │   └── TaskRepository.java          # Task repository
│   │       ├── service/
│   │       │   ├── AuthService.java             # Authentication service
│   │       │   └── TaskService.java             # Task service
│   │       ├── security/
│   │       │   ├── JwtTokenProvider.java        # JWT provider
│   │       │   ├── JwtAuthenticationFilter.java # Authentication filter
│   │       │   └── CustomUserDetailsService.java
│   │       └── exception/
│   │           ├── GlobalExceptionHandler.java  # Exception handler
│   │           └── TaskNotFoundException.java
│   └── resources/
│       └── application.yml                      # Application configuration
└── test/
    └── java/                                    # Tests
```

## ⚙️ Configuration

### application.yml
```yaml
server:
  port: 8080

spring:
  application:
    name: task-manager-api
  
  datasource:
    url: jdbc:h2:mem:taskdb
    username: sa
    password: password
    driver-class-name: org.h2.Driver
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    database-platform: org.hibernate.dialect.H2Dialect
  
  h2:
    console:
      enabled: true

jwt:
  secret: myVerySecretKeyForJwtTokenGenerationThatShouldBeLongEnough
  expiration: 86400000 # 24 hours

logging:
  level:
    com.aufgabe.danyil.test: DEBUG
```

## 🗄️ Database

The application uses **H2 Database** in-memory for easy deployment.

### H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:taskdb`
- Username: `sa`
- Password: `password`

### Database Schema

**users table:**
- id (BIGINT, PK)
- email (VARCHAR, UNIQUE)
- password (VARCHAR, encrypted)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

**tasks table:**
- id (BIGINT, PK)
- title (VARCHAR)
- description (TEXT)
- completed (BOOLEAN)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)
- user_id (BIGINT, FK)

## 🧪 Testing

### Postman Collection

1. **Import collection** from `postman_collection.json` file
2. **Create variable** `baseUrl` = `http://localhost:8080`
3. **Get JWT token** via `/api/auth/login`
4. **Add token** to header `Authorization: Bearer {{token}}`

### Testing Examples

1. **User registration:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

2. **Create task:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"title":"Test task","description":"Description"}'
```

3. **Get tasks:**
```bash
curl -X GET "http://localhost:8080/api/tasks?page=0&size=5" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## 🔒 Security

- **JWT tokens** with 24-hour expiration
- **BCrypt password encryption**
- **User-level authorization** - access only to own tasks
- **Input data validation**
- **CORS support**

## 📊 Pagination Features

- **Page size:** configurable (default 5)
- **Sorting:** by `createdAt`, `updatedAt`, `title` fields
- **Direction:** `asc` or `desc`
- **Filtering:** by completion status
- **Search:** by task title

### Pagination response example:
```json
{
  "tasks": [...],
  "currentPage": 0,
  "totalPages": 3,
  "totalElements": 15,
  "pageSize": 5,
  "first": true,
  "last": false,
  "empty": false
}
```

## 🐛 Error Handling

API returns standard HTTP error codes:

- **400 Bad Request** - validation errors
- **401 Unauthorized** - not authenticated
- **403 Forbidden** - access denied
- **404 Not Found** - resource not found
- **500 Internal Server Error** - internal error

### Error response format:
```json
{
  "timestamp": "2024-01-20T10:30:45",
  "status": 400,
  "error": "Bad Request",
  "message": "Task title cannot be empty"
}
```

## 🚀 Deployment

### For production:

1. **Change database** to PostgreSQL/MySQL
2. **Update application-prod.yml:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/taskdb
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}

jwt:
  secret: ${JWT_SECRET}
```

3. **Build JAR file:**
```bash
mvn clean package -Pprod
```

4. **Run:**
```bash
java -jar target/task-manager-api-1.0.0.jar --spring.profiles.active=prod
```

## 📝 TODO / Possible Improvements

- [ ] Add user roles (ADMIN, USER)
- [ ] Implement password reset via email
- [ ] Add task categories
- [ ] Implement due date notifications
- [ ] Add file attachments to tasks
- [ ] Implement API versioning
- [ ] Add Swagger/OpenAPI documentation
- [ ] Implement Docker containerization
- [ ] Add integration tests
- [ ] Implement caching
- [ ] Add task sharing between users
- [ ] Implement task templates
- [ ] Add task comments/notes
- [ ] Implement task priorities
- [ ] Add email notifications

## 🎯 API Endpoints Summary

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | User login |

### Tasks (requires authentication)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks (paginated) |
| POST | `/api/tasks` | Create new task |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |
| PATCH | `/api/tasks/{id}/complete` | Mark task as completed |
| PATCH | `/api/tasks/{id}/incomplete` | Mark task as incomplete |
| GET | `/api/tasks/status` | Filter tasks by status |
| GET | `/api/tasks/search` | Search tasks by title |
| GET | `/api/tasks/statistics` | Get task statistics |

### Test Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/test/public` | Public endpoint (no auth) |
| GET | `/api/test/protected` | Protected endpoint (auth required) |

## 🏗️ Architecture

The application follows a layered architecture:

```
Controllers → Services → Repositories → Database
     ↓
   DTOs ← Entities
```

- **Controllers**: Handle HTTP requests and responses
- **Services**: Business logic layer
- **Repositories**: Data access layer
- **DTOs**: Data transfer objects for API
- **Entities**: JPA entities for database mapping

## 📦 Dependencies

### Main Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
</dependencies>
```

## 👤 Author

Danyil  
📧 Email: [davydenkodanyil.com]  
🐙 GitHub: [https://github.com/DavydenkoDanyil]

## 📄 License

This project is created for educational purposes.

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
