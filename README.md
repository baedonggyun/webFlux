# WebFlux Project

This project is a Spring Boot application leveraging WebFlux for reactive programming. It also includes a custom circuit breaker implementation.

## Project Structure

### 1. Build and Configuration Files

- \`\`: Maven build configuration file.
- `**, **`: Maven Wrapper scripts for portability.
- `**, **`: Application configuration files.

### 2. Source Code

- **Main Application**

    - `WebFluxApplication.java`: Entry point for the Spring Boot application.

- **User Module**

    - `User.java`: User entity.
    - `UserController.java`: REST Controller for user-related operations.
    - `UserService.java`: Service layer handling business logic.
    - `UserRepository.java`: Repository layer for database interactions.

- **Circuit Breaker Module**

    - `CircuitBreakerAspect.java`: Aspect for managing circuit breaker logic.
    - `CircuitBreakerConfiguration.java`: Configuration for circuit breaker.
    - `CustomCircuitBreaker.java`: Custom implementation of a circuit breaker.

- **Shop Module**

    - `ShopController.java`: REST Controller for shop operations.
    - `ShopService.java`: Business logic for shop operations.
    - `dto/CombinedResult.java`: Data transfer object for combined results.
    - `entity/Seller.java`, `entity/Shop.java`: Entity definitions.
    - `repository/`: Repositories for database access.

### 3. Resources

- `application.properties`: Property-based configuration.
- `application.yml`: YAML-based configuration.

### 4. Tests

- `WebFluxApplicationTests.java`: Unit tests for the application.

## How to Run

1. **Prerequisites**

    - Java 11 or higher.
    - Maven installed (optional if using Maven Wrapper).

2. **Build and Run**

```bash
# Clean and build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

3. **Access the Application**
    - The application runs by default on `http://localhost:8080`.

## Features

- **Reactive Programming**: Built with Spring WebFlux for non-blocking operations.
- **Custom Circuit Breaker**: Includes a robust circuit breaker mechanism implemented with AOP.
- **Modular Design**: Divided into separate modules for users and shop functionalities.

## Customization

- Modify `application.properties` or `application.yml` to customize application settings.
- Extend repositories and services as per business requirements.

## License

This project is licensed under the [MIT License](LICENSE).

## Contribution

Feel free to fork this repository and submit pull requests for improvements or new features.

