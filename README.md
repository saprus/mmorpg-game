# 🧙‍♂️ MMORPG API Catalog

This is a Spring Boot REST API for managing players, characters, and inventory items in a fictional MMORPG-style game.

---

## 🚀 Features

- Full CRUD APIs for:
  - Players
  - Characters
  - Inventory items
- Role-Based Access Control (RBAC): PLAYER vs ADMIN
- Data ownership validation (PLAYER can only modify their own resources, except ADMIN)
- Global error handling (clean JSON errors)
- Enum-based serialization (`ItemType`, `Role`) with case-insensitive support
- Containerized with Docker + Postgres

---

## 🧰 Technologies Used

- Java 21
- Spring Boot 3
- PostgreSQL
- Docker & Docker Compose
- DTOs + Mapper Pattern
- Spring Security (lightweight, for RBAC only)

---

## 🔐 Authorization

All protected endpoints require an `X-Player-Id` header to simulate a logged-in user

### 🛡️ Role-Based Access Control (RBAC)

- `PLAYER` role can only access and modify their own resources
- `ADMIN` role can access and modify any player's resources

### Required Header

```http
X-Player-Id: <playerId>
```

---

## ⚙️ Setup Instructions

### 1️⃣ Build the JAR

```bash
mvn clean package -DskipTests
```
### 2️⃣ Start with Docker

```bash
docker compose up --build 
```

---

## 📘 Swagger API Documentation

Interactive docs are available once the app is running:

- [http://localhost:8080/swagger-ui.html?configUrl=/v3/api-docs/swagger-config](http://localhost:8080/swagger-ui.html?configUrl=/v3/api-docs/swagger-config)


---

## 🌟 Bonus Features Implemented

In addition to the core requirements, this submission includes the following optional enhancements:

- ✅ Swagger/OpenAPI documentation via Springdoc
- ✅ Role-Based Access Control (RBAC) with custom `X-Player-Id` header logic
  - Enum-based validation for `ItemType` and `Role` with case-insensitive support
  - Ownership checks for all protected resources
- ✅ Modular or Layered Architechture (DTO + Mapper) 
- ✅ Global error handling using `ResponseStatusException`


---

## 📝  Notes

- Automated tests were deprioritized in favor of complete end-to-end architecture and deployment
- Test scaffolding (JUnit, MockMvc, Mockito) is ready and can be expanded if desired
