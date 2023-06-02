# Weather App

This project is a Spring Boot Vaadin application that utilizes the Maven package manager. It includes integration with PostgreSQL using Docker Compose. The application also uses Liquibase for managing database script versioning.

## Prerequisites
To run this project, ensure that you have the following installed:

- Docker
- Docker Compose

## Running the Application
1. Navigate to Project Directory
2. Start the PostgreSQL, Redis and App using Docker Compose. Run the following command:
  ```
  docker-compose up
  ```
3. All services should be up, and you can access the application using the following URL,
```
localhost:8080
```

## Database Script Versioning

This project uses Liquibase for managing database script versioning. When running the application for the first time, Liquibase will automatically execute the required scripts and create the necessary database tables.

# Default Users

Upon running the application and initializing the database, the following default users will be inserted:

* User 1:
  * Username: user
  * Password: password

* User 1:
  * Username: admin
  * Password: password


## Pagination
Please note that the Search Location page does not have pagination functionality due to limitations with the Open-Meteo API. The API we call does not support pagination for retrieving weather information.

However, I have implemented pagination support on the Favorite Locations page. You can navigate through the different pages to view and manage your favorite locations effectively.
