# JWT
Generates token JWT with [jjwt](https://github.com/jwtk/jjwt).

## Stack
- [jjwt] (https://github.com/jwtk/jjwt/) 0.9.1
- Java 11
- Spring Boot 2.6.1
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway (version control for your database)
# Docker
    docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres