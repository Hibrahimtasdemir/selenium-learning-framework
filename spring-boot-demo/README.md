# Spring Boot Demo

Minimal CRUD example with Spring Boot, H2 and Spring Data JPA.

Run locally:

```bash
cd spring-boot-demo
./mvnw spring-boot:run
```

API endpoints:
- GET /api/todos
- GET /api/todos/{id}
- POST /api/todos
- PUT /api/todos/{id}
- DELETE /api/todos/{id}

Tests:

```bash
cd spring-boot-demo
./mvnw -DskipTests=false test
```
