<img width="49%" src="https://github.com/user-attachments/assets/350e01d0-d8f0-432d-83b4-4bda66ebd559" alt="Preview of application main page.">
<img width="49%"  src="https://github.com/user-attachments/assets/59b67fab-8fc0-4b5b-8790-480bad74d2e0" alt="Preview of application location page.">

# Rick and Morty App

A web application built with Java and Spring Boot that allows CRUD operations on data retrieved from the external [Rick and Morty API](https://rickandmortyapi.com/).

## 🚀 Features

- Fetch and display characters, locations, and episodes from the Rick and Morty universe
- Perform CRUD operations on the retrieved data
- User interface for easy interaction with the application
- Secure API endpoints with authentication
- Automatic data updates from the external API

## 📁 Project Structure

The project is organized into several modules:

- **rick-morty-data-client**: Client for interacting with the external Rick and Morty API
- **rick-morty-data**: Data models and repositories
- **rick-morty-security**: Security configuration and authentication
- **rick-morty-ui**: User interface components
- **rick-morty-updater**: Service for updating data from the external API
- **rick-morty-web-api**: REST API endpoints

## 🔧 Technologies

- Java 
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring MVC using Thymeleaf
- Spring Scheduler, Spring Cache
- MariaDB
- Gradle
- HTML/CSS, Bootstrap
- RESTful API

## 🛠️ Setup and Installation

### Prerequisites

- JDK 11 or higher
- Gradle
- Your favorite IDE (IntelliJ IDEA recommended)

### Installation Steps

1. Clone the repository
2. Build the project
3. Initialize the database
4. Run the application
5. Access the application at `http://localhost:8080`
