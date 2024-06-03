# WeatherApp

WeatherApp is a user-friendly web application designed to provide detailed weather forecasts for various locations.
It contains all the necessary configuration and some placeholder files to get you started.

Vaadin 24.0 is based on Jakarta EE 10.0 and requires Java 17.

## Running the Application

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different
IDEs](https://vaadin.com/docs/latest/flow/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

Run application using
```
mvn tomee:run
```
```
mvn wildfly:run
```

Open [http://localhost:8080/WeatherApp](http://localhost:8080/WeatherApp) in browser.

If you want to run your app locally in the production mode, run using
```
mvn clean package wildfly:run -Pproduction
```
### Database Configuration
Postgresql 42.2.23 is used for the application.

To configure database go the `DatabaseManager.java` file (see the project structure to locate) to set up the necessary changes.
Additionally a script is provided with the project placed in the resoureces folder.
Run the script to generate necessary tables & data.
```
List of users for the application 
- username: john_doe,   password: password123
- username: jane_smith, password: pass456
- username: admin,      password: admin123
```
#### Deployment

The project is a standard Java/Jakarta EE application, so you can deploy it as you see best, via IDE or using Maven plugins. Wildfly and TomEE plugins are pre-configured for easy testing. Wildfly plugin is used for integration tests. Currently only Wildfly properly supports Java 17.

The application can be deployed on the [Apache TomEE](http://tomee.apache.org/) server via the `tomee-maven-plugin`, which supports hot deployment of code changes (via the `reloadOnUpdate` setting).
This means that you can make changes to the code in your IDE while the server is running, recompile, and have the server automatically pick up the changes and redeploy them.
This setting is enabled by default in this project.

##### Project structure

- This setup uses [Vertical Layout](https://vaadin.com/components/vaadin-vertical-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/` contains the client-side JavaScript views of your application.
- `themes` folder in `frontend/` contains the custom CSS styles.
```
- WeatherApp
  │   pom.xml
  │   README.md   
  └───src
  └───main
  └───java
  └───com
  └───vaadin
  └───example
  ├───api
  │       WeatherApiClient.java
  │
  ├───config
  │       DatabaseManager.java
  │
  ├───model
  │       Location.java
  │       Users.java
  │
  ├───repository
  │       LocationRepository.java
  │       UserRepository.java
  │
  ├───service
  │       LocationService.java
  │       UserService.java
  │  
  ├───util
  │       Tools.java
  │
  └───view
  │       FavoriteLocations.java
  │       LoginView.java
  MainView.java

  ```
##### Business Logic
- any user can search Location (name, lat, long) by city name
- filter option is available
- only registered users can see weather forecast by clicking on location
- registered users also can hourly details weather info by clicking on specific date
