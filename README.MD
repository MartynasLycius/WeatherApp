# Weather App

This project shows Daily and Hourly basis weather forecast service.Call this service to get Updated forecast data 

 https://api.open-meteo.com/v1/forecast

## Running the application

Code pull from repository.
Project import as Maven Project in IDE.
Start/Run database server.
Import weather_app.sql in database from project/  folder (structure and data)
Update following DB server credential in application.properties file (if needed)

spring.datasource.username=root
spring.datasource.password= 

Build and Run the application 
Application will run on browser at http://localhost:8080

Login credential :

user name : admin
passoword : admin1234 

## Project structure

- `MainLayout.java` in `src/main/java` contains the navigation setup (i.e., the
  side/top bar and the main menu). 
- `model` package in `src/main/java/backend` contains entities of the application.
- `repository` package in `src/main/java/backend` contains the repository interfaces of the application.
- `service` package in `src/main/java/backend` contains the service classes of the application.
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.


