# SongsApp (MusicApp)

MusicApp — Spring Boot microservice example (Songs service, UserService, Eureka server).

This repository contains a multi-module Spring Boot microservices example that demonstrates a small ecosystem of services for managing songs and users, service discovery with Eureka, and observability hooks for Prometheus. The goal is to showcase a modular microservice layout, clear separation of concerns, and a straightforward way to run, build, and test each service independently or together.

NOTE: Replace any example ports, environment variables, or DB connection settings below with the actual values found in each module's `src/main/resources/application.properties` or `application.yml`.

Table of contents
- Overview
- Modules
- Tech stack
- Architecture
- Getting started (prereqs)
- Run services locally
- Build and package
- Configuration & environment variables
- Example REST endpoints
- Testing
- Observability
- Contributing
- License

Overview
--------
MusicApp is organized as multiple modules under this single repository. Each module is a standalone Spring Boot application that can be started independently. Typical usage is to run Eureka discovery first (if you want service registration) and then run the Songs and UserService applications which will register with Eureka.

Modules
-------
- Songs/: Service that manages songs/tracks metadata (title, artist, album, duration, etc.). This is a standard Spring Boot/Maven module located under the `Songs/` directory.
- UserService/: Service for managing user accounts and authentication-related user data.
- Eurekaserver/: A small Netflix Eureka discovery server used for service registration and discovery in local development.
- prometheus/: Example Prometheus configuration files (if present) to scrape metrics from services.

Tech stack
----------
- Java 17+ (or the JDK version defined in each module)
- Spring Boot (Spring Web, Spring Data, Spring Actuator)
- Spring Cloud Netflix Eureka for service discovery
- Maven build system (multi-module)
- Prometheus (optional) for metrics scrapping
- (Optional) PostgreSQL / H2 or other databases depending on module configs

Architecture
------------
Each module follows a typical layered Spring Boot architecture:
- Controller layer: REST endpoints
- Service layer: Business logic
- Repository layer: Data access (Spring Data JPA repositories)

Services are independent; they communicate via REST. Eureka provides service discovery so services can locate each other by name during development.

Getting started (prereqs)
-------------------------
- JDK 17+ installed and JAVA_HOME set
- Maven 3.6+
- (Optional) Docker & docker-compose if you prefer containerized runs
- (Optional) a running database (Postgres/MySQL) if you configure persistence; otherwise the projects can be configured to use H2 in-memory DB for quick tests

Run services locally
--------------------
Start each module from the repository root or from the module directory. Open terminals for each service:

1. Start Eureka server (recommended first so services can register):

   cd Eurekaserver
   mvn spring-boot:run

2. Start UserService:

   cd ../UserService
   mvn spring-boot:run

3. Start Songs service:

   cd ../Songs
   mvn spring-boot:run

Notes:
- Check `src/main/resources/application.properties` or `application.yml` in each module for ports and database configuration. If ports conflict, edit the properties or pass `-Dserver.port=...` when running.
- If services expect Eureka at a specific host/port, ensure Eureka is started with that host/port or update service configs.

Build and package
-----------------
To build all modules from the repo root:

mvn clean package

This will create artifacts in each module's `target/` directory. You can run the resulting executable jars:

java -jar Songs/target/songs-<version>.jar
java -jar UserService/target/userservice-<version>.jar
java -jar Eurekaserver/target/eurekaserver-<version>.jar

Configuration & environment variables
-------------------------------------
Each module may use environment variables or properties for configuration. Common variables you may see or want to set:
- SPRING_DATASOURCE_URL / spring.datasource.url
- SPRING_DATASOURCE_USERNAME / spring.datasource.username
- SPRING_DATASOURCE_PASSWORD / spring.datasource.password
- EUREKA_CLIENT_SERVICEURL_DEFAULTZONE (where to find Eureka)
- SERVER_PORT / server.port

Provide a `.env` or set environment variables when running with Docker or docker-compose.

Example REST endpoints (replace with real endpoints from your controllers)
-----------------------------------------------------------------------
- Songs service:
  - GET /api/songs — List songs
  - GET /api/songs/{id} — Get song details
  - POST /api/songs — Create new song
  - PUT /api/songs/{id} — Update song
  - DELETE /api/songs/{id} — Delete song

- UserService:
  - POST /api/users — Create user
  - GET /api/users/{id} — Get user details
  - POST /api/auth/login — Authenticate user (if implemented)

Use Postman, HTTPie, or curl to test the endpoints. Example:

curl -X GET http://localhost:8080/api/songs

(Adjust host/port to the running service's port.)

Testing
-------
Run unit and integration tests for an individual module:

cd Songs
mvn test

Run tests across all modules from repo root:

mvn test

Observability
-------------
- Each service can expose actuator endpoints (e.g., /actuator/metrics, /actuator/prometheus) if Spring Boot Actuator and micrometer are enabled. Configure Prometheus to scrape the services using files in the `prometheus/` directory if present.

- Check `application.properties` for actuator endpoint configuration and security settings.

Contributing
------------
- Fork the repository
- Create a feature branch: `git checkout -b feat/my-feature`
- Commit changes and open a pull request
- Add tests for new features

License
-------
Add a LICENSE file to indicate the project license (MIT, Apache-2.0, etc.). If this repository is public and you want others to reuse it, adding an explicit license is recommended.

Contact / Additional customization
----------------------------------
If you want, I can:
- Extract and document actual default ports, environment variables, and DB settings by reading each module's `application.properties` or `application.yml`.
- Add example curl commands tailored to your real endpoints.
- Add a LICENSE file and a sample `docker-compose.yml` to run all services together.

If you'd like me to include the specific configuration details in this README, tell me whether I should read the `application.properties` files in `Songs/`, `UserService/`, and `Eurekaserver/` and I'll update the README to include exact ports, DB URLs, and example endpoints.