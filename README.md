# User-Service (Production)
A Spring Boot microservice designed to handle user data securely across PostgreSQL and MongoDB
<p align="left">
  <img src="https://skillicons.dev/icons?i=java,spring,mongo,postgresql,kafka,docker,gradle,postman,git"/>
</p>

# Features

- Hybrid Data Storage: Utilizes PostgreSQL for structured relational data (credentials, crypt-password, etc.) and MongoDB for flexible document-based data (e.g., user profiles, preferences), ensuring optimal performance and scalability
- User CRUD Management: Provides endpoints to get, read, update, and delete user profiles, with data persisted in PostgreSQL and MongoDB.
- Event-Driven Kafka Events: Publishes user-related events (profile update) to Kafka, enabling asynchronous processing and integration with other microservices

# Prerequisites
- Java Development Kit(JDK): Version 17 or higher
- Gradle: For project build and dependency management
- Docker: For building containers
- Vault: Keep your API keys, JWT tokens & secure data
  
  ```sh
  java --version
  gradle --version
  docker --version
  ```
