# MMORPG API Catalog

This is a Spring Boot REST API for managing players, characters, and inventory items in a fictional MMORPG-style game.

---

## Features

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

## Technologies Used

- Java 21
- Spring Boot 3
- PostgreSQL
- Docker & Docker Compose
- DTOs + Mapper Pattern
- Spring Security (lightweight, for RBAC only)

---

## Authorization

All protected endpoints require an `X-Player-Id` header to simulate a logged-in user

### Role-Based Access Control (RBAC)

- `PLAYER` role can only access and modify their own resources
- `ADMIN` role can access and modify any player's resources

### Required Header

```http
X-Player-Id: <playerId>
```

---

## Setup Instructions

### Build the JAR

```bash
mvn clean package -DskipTests
```

```bash
docker compose up --build 
```