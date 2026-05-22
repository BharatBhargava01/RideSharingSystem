# 🚗 University Ride-Sharing System

A comprehensive, hybrid Java application combining a **Spring Boot** backend API with a rich **JavaFX** desktop interface. This system is designed for university students and staff to seamlessly offer and book rides to and from campus.

## 🌟 Key Features

*   **Ride Management:** Drivers can easily offer rides by specifying their source, destination, and available seats.
*   **Booking System:** Passengers can search for available rides and book seats. The system handles concurrency for bookings using multithreading.
*   **Smart Matching Engine:** Efficiently matches passengers looking for rides with available drivers heading in the same direction.
*   **Real-Time Notifications:** Alerts users upon successful ride confirmations and updates.
*   **Administrative Panel:** Dedicated admin controls for managing users, monitoring ride history, and system oversight.
*   **Secure Authentication:** User login system to authenticate drivers, passengers, and administrators.

## 🛠️ Technology Stack

**Backend (API & Services):**
*   **Java 17:** Core programming language.
*   **Spring Boot 3.x:** Framework for creating the REST API (`Spring Web MVC`) and handling data (`Spring Data JPA`).
*   **PostgreSQL:** Relational database for persistent storage. Raw JDBC is used for direct database interactions in custom DAOs.
*   **JUnit 5:** Testing framework for unit and integration testing.

**Frontend (Desktop Client):**
*   **JavaFX:** For building the graphical user interface.
*   **FXML:** XML-based markup language for defining the UI layout (e.g., `OfferRide.fxml`, `Booking.fxml`, `AdminPanel.fxml`).
*   **CSS:** For styling the JavaFX components and delivering a polished UI experience.

**Tools & Utilities:**
*   **Maven:** Dependency management and build automation.
*   **Lombok:** Reduces boilerplate code (getters, setters, constructors).

## 🗂️ Project Structure

The project is cleanly separated into distinct architectural layers:

*   `app/`: Main application entry points (Spring Boot application runner and JavaFX Application class).
*   `controller/`: Handles HTTP requests (Spring `@RestController`) and UI interactions (JavaFX `@FXML` controllers).
*   `model/`: Plain Old Java Objects (POJOs) representing domain entities like `Ride`, `Booking`, `User`, `Student`, and `Notification`.
*   `database/`: Data Access Objects (DAOs) and connection utilities to interface directly with PostgreSQL.
*   `service/`: Core business logic, including `MatchingEngine`, `RideService`, and `NotificationService`.
*   `threads/`: Multithreading logic to handle concurrent operations like simultaneous ride bookings.
*   `resources/fxml/`: User interface layout files.
*   `resources/css/`: Stylesheets for the application.

## 🚀 Getting Started

### Prerequisites
1.  **Java Development Kit (JDK) 17** installed.
2.  **Maven** installed.
3.  **PostgreSQL** installed and running on default port `5432`.

### Database Setup
Ensure you have a PostgreSQL database named `rideshare`. 
By default, the application is configured to connect with:
*   **Username:** `postgres`
*   **Password:** `root`
*(You can change these in `src/main/resources/application.properties` if needed).*

### Running the Application
To run the Spring Boot backend server, open a terminal in the root directory and execute:
```bash
./mvnw spring-boot:run
```
*(Note: To launch the JavaFX UI, you will need to execute the MainApp class located in the `app` package).*

### Running Tests
To execute the suite of JUnit tests, run:
```bash
./mvnw test
```
