# Ride Sharing System

A backend application for a university ride-sharing system, built with Spring Boot and Java. It allows students to book rides offered by drivers (other students/staff).

## Technologies Used
- **Java 17**
- **Spring Boot 3.x**
  - Spring Web MVC
  - Spring Data JPA
- **PostgreSQL** (Database)
- **Lombok** (Boilerplate reduction)
- **Maven** (Build Tool)

## Architecture & Data Model

The application uses an Entity-based data model with the following core components:

- **Users (`User.java`)**: Implements the Single Table Inheritance strategy. All users (Students, Drivers, Admins) are stored in a single `users` table differentiated by a `role` column.
- **Rides (`Ride.java`)**: Represents a trip created by a `Driver`. Includes details such as source, destination, departure time, available seats, and fare.
- **Bookings (`Booking.java`)**: Represents a reservation made by a `Student` for a specific `Ride`.

## API Endpoints

### Rides
- `POST /api/rides?driverId={id}`: Create a new ride.
- `GET /api/rides`: View all available rides.
- `DELETE /api/rides/{id}`: Delete a specific ride.

### Bookings
- `POST /api/bookings?rideId={id}&studentId={id}`: Book a seat on a ride. Includes built-in validation (e.g., handles `SeatUnavailableException` when no seats are left).

## Setup Instructions

### Prerequisites
1. Java 17 installed.
2. Maven installed (or use the provided `mvnw` wrapper).
3. PostgreSQL installed and running locally.

### Configuration
Update the `src/main/resources/application.properties` file with your PostgreSQL database credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rideshare
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```

### Running the Application
To build and run the application, use the Maven wrapper:

```bash
# Clean and compile
./mvnw clean install

# Run the Spring Boot application
./mvnw spring-boot:run
```

The server will start on `http://localhost:8080` by default.
