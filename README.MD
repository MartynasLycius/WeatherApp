# Weather App

This app will produce an UI for checking weather forecast with daily and hourly details for any specific location using [open-meteo](https://open-meteo.com/) free public APIs.

## Features
* Login
* Dashboard
* Search location with filter
* Weather details
  * Current weather details
  * Next 7 days weather details
  * Hourly weather details for specific day
* Favorite locations save and delete

## Technologies Used
* Spring boot
* Maven
* Vaadin
* MySQL

## Build and Run

### Prerequisites
* Java 17 or later
* Maven 3.6+
* MySQL database

### Run
1. Start MySQL and create a database for the application.

2. Go to `src/main/resources` and open `application-dev.properties` file. Change following properties with your properties.
```
    spring.datasource.url = jdbc:mysql://host:port/db-name
    spring.datasource.username = root
    spring.datasource.password = password
```

> **INFO**
>
>
> We will run the application only in development mode for demo as Vaadin needs license for production build. 
> If we want to build in production mode, we need to update datasource url in `application-prod.properties` file as well.
>
3. Go to the project directory and run the following command:
```
    mvnw spring-boot:run
```
4. Open the browser and put following URL:
```
    http://localhost:8080/WeatherApp
```

## Default Users
You will need users to login.

2 default users will be created when the application runs.

* User 1 (ADMIN role):
  * username: admin
  * password: admin
  

* User 2 (USER role):
    * username: user
    * password: user


