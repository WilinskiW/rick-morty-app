<img width="49%" src="https://github.com/user-attachments/assets/350e01d0-d8f0-432d-83b4-4bda66ebd559" alt="Preview of application main page.">
<img width="49%"  src="https://github.com/user-attachments/assets/59b67fab-8fc0-4b5b-8790-480bad74d2e0" alt="Preview of application location page.">

# Rick and Morty App

A web application built with Angular and Spring Boot that allows CRUD operations on data retrieved from the external [Rick and Morty API](https://rickandmortyapi.com/).

## 🚀 Features

- Fetch and display characters, locations, and episodes from the Rick and Morty universe
- Perform CRUD operations on the retrieved data
- User-friendly interface built with Angular.
- Secure API endpoints with JWT authentication
- Automatic data updates from the external API

## 📁 Project Structure

The project is organized into several modules:

- **rick-morty-client**: Application frontend based in Angular
- **rick-morty-data-client**: Client for interacting with the external Rick and Morty API
- **rick-morty-data**: Data models and repositories
- **rick-morty-security**: Security configuration and authentication using JWT stored safely in cookies.
- **rick-morty-updater**: Service for updating data from the external API
- **rick-morty-web-api**: REST API endpoints

## 🔧 Technologies
### Frontend
- Angular
- TypeScript
- HTML
- CSS, Bootstrap

### Backend
- Java 21
- Spring Boot
- Spring Security - JWT
- Spring Data JPA
- MariaDB
- Gradle
- RESTful API

## 🛠️ Setup and Installation

### Prerequisites
- JDK 11 or higher
- Gradle
- Node.js + npm
- Angular CLI
- Docker (to run database)
- Your favorite IDE (IntelliJ IDEA recommended)

### Installation Steps

#### Backend
1. Clone the repository.
2. Build the project using Gradle.
3. Initialize the database using `docker-compose.yml` file.
4. Run the `rick-morty-web-api` module.
5. Access the application at `http://localhost:8081`.

#### Frontend
1. Enter `cd rick-morty-client`.
2. Run `npm install` in rick-morty-client folder.
3. Run `ng serve` to start the Angular application.
4. Access the application at `http://localhost:4200`.
