# NotificationService

A Spring Boot REST API for managing user notifications with multi-channel delivery (Email, SMS, Push). Built with Java 21, PostgreSQL, and JWT authentication.

## Features

- **User Authentication**: Register and login with JWT tokens
- **Notification Management**: Create, read, update, delete notifications
- **Multi-Channel Delivery**: Email, SMS, and Push notifications via Strategy Pattern
- **Secure API**: JWT-protected endpoints with ownership validation
- **Input Validation**: Request validation with consistent error responses
- **Extensible Architecture**: Easy to add new notification channels

## Prerequisites

- Java 21 or later
- PostgreSQL 12+
- Gradle

## Installation

### 1. Clone the repository
```bash
git clone <repository-url>
cd NotificationService
```

### 2. Configure PostgreSQL

Create database and user:
```sql
CREATE DATABASE notification_db;
CREATE USER notification_user WITH PASSWORD 'password123';
GRANT ALL PRIVILEGES ON DATABASE notification_db TO notification_user;
```

### 3. Set environment variables

Create an `.env` file or configure `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/notification_db
spring.datasource.username=notification_user
spring.datasource.password=password123
jwt.secret=your-secret-key-here-min-32-chars
```

### 4. Run the application

```bash
./gradlew bootRun
```

Server starts on `http://localhost:8080`

## API Endpoints

### Authentication (Public)

```
POST /auth/register
{
  "email": "user@example.com",
  "password": "securePass123"
}
Response: JWT token (201)

POST /auth/login
{
  "email": "user@example.com",
  "password": "securePass123"
}
Response: { "token": "jwt-token" } (200)
```

### Notifications (Requires JWT)

```
GET /notifications
Response: List of user's notifications (200)

GET /notifications/{id}
Response: Notification details (200)

POST /notifications
{
  "title": "Alert",
  "content": "Your message",
  "channel": "EMAIL"
}
Response: Created notification (201)

PUT /notifications/{id}
{
  "title": "Updated",
  "content": "New content",
  "channel": "SMS"
}
Response: Updated notification (200)

DELETE /notifications/{id}
Response: No content (204)
```

**Channels**: `EMAIL`, `SMS`, `PUSH`

### Error Responses

```
400 Bad Request: Validation failed
{
  "status": 400,
  "message": "Email should be valid"
}

401 Unauthorized: Missing or invalid JWT

403 Forbidden: Access denied (ownership validation)

500 Internal Server Error: Server error
```

## Technical Decisions

### 1. Strategy Pattern for Channels
Each notification channel (Email, SMS, Push) is a separate implementation of `NotificationSender`. This allows adding new channels without modifying existing code—just create a new class and Spring registers it automatically.

### 2. Stateless JWT Authentication
We use JWT tokens instead of sessions. This makes the API scalable and stateless. The `userId` is extracted from the token in each request.

### 3. Global Exception Handler
A centralized `GlobalExceptionHandler` intercepts all exceptions and returns consistent error responses with proper HTTP status codes.

### 4. Ownership Validation
All notification operations (update, delete, get) validate that the requested notification belongs to the authenticated user.

### 5. Spring Security with Custom Filter
We use Spring Security with a custom `JwtFilter` to validate tokens while keeping the authentication logic simple and testable.

## Project Structure

```
src/main/java/com/ericksilva/notificationapi/
├── auth/              # JWT and authentication
├── user/              # User entity and repository
├── notification/      # Notification CRUD
├── channel/           # Notification delivery channels
└── exception/         # Global error handling
```

## Testing

Register a user and get a token:
```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password123"}'
```

Create a notification:
```bash
curl -X POST http://localhost:8080/notifications \
  -H "Authorization: Bearer <YOUR_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"title":"Test","content":"Hello","channel":"EMAIL"}'
```

## License

MIT
