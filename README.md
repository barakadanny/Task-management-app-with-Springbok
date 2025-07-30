# Task Management API

A clean, modular Spring Boot REST API for managing tasks and task lists — built with best practices in mind.  
Designed to be scalable, maintainable, and easy to extend.

## Features

- CRUD operations for Task Lists and Tasks
- DTOs and Mappers for clean separation between API and data layers
- Robust validation and detailed error handling
- Uses Spring Data JPA for database interaction
- Follows layered architecture (Controller → Service → Repository)
- Enum validation with user-friendly error messages
- Global exception handling for consistent API responses

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA / Hibernate
- H2 / PostgreSQL (configurable)
- Maven / Gradle  

## Getting Started

1. Clone the repo
2. Configure your database connection in `application.properties`
3. Run the application in IntelliJ IDEA or another java supported code editor