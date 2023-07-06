# Weather Application (Meteo Weather Application)

An excellent & modern weather application built with **Vaadin** and **Spring Boot** using
[Open Meteo](https://open-meteo.com/en) API. Features, dashboard style weather forecasting, Authentication control and more.

[![meteo-weather-app-preview]()]

### Built with

* java 17 [Open JDK](https://openjdk.org/projects/jdk/17/)
  or [intelliJ IDEA jdk Guide](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk)
* Vaadin 24.1.0
* Spring Boot 3.1.0
* PostgreSQL 14
* and ❤️

### Features

1. Location search and filter
2. Login/Logout (username: user and password: 123123123)
3. Multiple User support
4. Save/Bookmark Location
5. Show weather details of selected Location (from search result / bookmark)
6. Current location weather (**requires Location Permission**)
7. **Beautiful Weather Dashboard with Tiles/Widgets for weather specific information. (Check screenshot)**
8. Weekly weather overview
9. Daily weather breakdown (with 24hrs forecasting)
10. Responsive layout design **(3/2/1 column design)** (Not mobile first)
11. PWA enabled
12. Rest you need to find out (if any 😜)

### Run

1. Import this project to your favourite IDE (I prefer intelliJ IDEA).
2. Sync Maven Repositories
3. Check properties file for postgresql Database Name, Database User name and password.(change if required)

 ```sh
WeatherApp/src/resources/application.properties
```

4. create a user/login role with name -> **root** and password -> **password** (if you have skiped step 3)
5. create a database and name it **weather-db** and select **root** as owner from the dropdown. (if you have skiped step
   3)
6. Make sure you have **Java 17**
7. Enable Lombok plugin. If required, install.
7. Start PostgreSQL server, Run MeteoWeatherApplication and enjoy.

### Common FAQ

```sh
Question: Can not start application. Getting exception 
org.postgresql.util.PSQLException: Connection to localhost:5432 refused. Check that the hostname and port are correct and that the postmaster is accepting TCP/IP connections.
```

Solution: Please start postgreSQL server.

```sh
Question: Getting java.lang.UnsupportedClassVersionError
```

Answer: This app requires JAVA 17

```sh 
Question: Getting java.lang.UnsupportedOperationException
```

Answer: Please check you are connected to internet. This app requirest Internet connection to run.

```sh 
Question: How do I login? / Username password?
```

Answer: **username: wahed** and **password:123123123** or<br>
**username: user** and  **password:123123123**

```sh 
Question: Something not covered here or a bug?
```
Answer: Please create an **issue**
## License

MIT License

## Author

Md. Waheduzzaman ([website 🌐])

[website 🌐]:https://waheduzzaman.com