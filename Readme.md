# Banking app

## TLDR

This is project is a fullstack banking application that supports full user lifecycle management, including user creation, deactivation/deletion, authentication (login/logout), and multi-account functionality.

It can simulate deposits, withdrawals, and transfers, all of which are logged in a local PostgreSQL database to ensure data persistence while the server is running.

The project implements custom authentication, password hashing, encryption logic, and session management because I’m fascinated by cybersecurity and wanted to learn how such systems are built at a low level on the backend.
(In production, you should use Spring Security.)

It uses optimized manual SQL queries, loaded from the JAR into public static final strings and executed using Spring’s JDBC Template, ensuring fast, efficient, and fully traceable queries.

Architecturally, the project follows the Separation of Concerns principle and uses a clear DAO → Service → Controller structure.

On the frontend I've used plain js for

## Installation guide:

1. Clone this repository
2. Ensure Docker is running
3. Start the PostgreSQL database from the project root:

```bash
docker compose up -d
```

4. Open the project in your editor/IDE.
5. Build and run the backend using the Maven Wrapper:

```bash
./mvnw clean compile
./mvnw spring-boot:run
```

6. Open your Browser and navigate to:
   http://localhost:8080/pages/Index.html

7. Enjoy the yet crude but fully functional UI, and the backend.
8. To inspect the database’s contents or validate persistence, use a tool such as DBeaver.
   Database credentials can be found in:

```css
src/main/resources/application.properties
```

## Architecture

The most important logic chains are documented via flowcharts and sequence diagrams in [Architecture.md](Architecture.md).