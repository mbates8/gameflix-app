# GameFlix Auth Service

This is the backend authentication service for GameFlix, implemented in Spring Boot.  
It provides two REST endpoints:

- **POST** `/register` – register a new user with a unique username and password
- **POST** `/login` – log in with existing credentials

Passwords are hashed with BCrypt and stored in an in-memory H2 database.

---

## Prerequisites

- Java 17 (or higher)
- Gradle (optional, you can use the wrapper)
- Docker (for containerization)

---

## Local Build & Run

1. Build the application JAR:
   ```bash
   ./gradlew clean build