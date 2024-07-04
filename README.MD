# Weather Application
## Requirements:
You need to use the Weather API provided here https://open-meteo.com/en to make the weather application. 
1. Call the API to get the locations by city name. Make the paging available with 10 items per page and a filter to filter by location name.
2. When clicked on the location it should show the weather forecast for the location with the temperature, surface wind and rain hourly. The interface should let you first see the daily forecast and when clicked it then should show the forecast hourly for the day you selected.
3. If you UI contains a chart it is a bonus. The interface should be convenient for user and rely on best usability and design practises
4. Only logged on users should be able to see the weather forecasts.
5. User should be able to mark the location favourite and have the favourite location list for quick access.

All the other specific requirements are up to you

## Technology used to develop this application:
* **Frontend:** Vaadin Flow 24.0 and CDI 15.0
* **Backend:** Jakarta EE 10.0.0
* **Database:** MySql
* **Project Build System:** Maven
* **Database connection:** JDBC
* **Charts:** Apex Chart addons for Vaadin Flow
* **Application Server:** Tom EE 10.0.0-M1

## Prerequisite Tools:
* **JDK-17** (Download: From Oracle or other)
* **MySql Server**(I used Xampp > Mysql Module)(Download: https://sourceforge.net/projects/xampp/files/XAMPP%20Windows/8.0.30/xampp-windows-x64-8.0.30-0-VS16-installer.exe)
* **Maven** (Download: https://dlcdn.apache.org/maven/maven-3/3.9.8/binaries/apache-maven-3.9.8-bin.zip)
* **Git** (Download: https://github.com/git-for-windows/git/releases/download/v2.45.2.windows.1/Git-2.45.2-64-bit.exe)

## Database Setup:
* Open your mysql console, and run sql statements defined in **weather_api.sql**
* Update your database information on **src/main/java/com/nashid/weatherapp/core/utils/DBConnectionManager.java**
```java
  private static final String URL = "jdbc:mysql://localhost:3306/weather_app?autoReconnect=true&useSSL=false";
  private static final String USER = "root";
  private static final String PASSWORD = "";
  
```

## Running application:
* Clone the repository
```bash
  git clone https://github.com/JITUaiub/WeatherApp.git
  cd WeatherApp
```
* Update your database information on **src/main/java/com/nashid/weatherapp/core/utils/DBConnectionManager.java**
```java
  private static final String URL = "jdbc:mysql://localhost:3306/weather_app?autoReconnect=true&useSSL=false";
  private static final String USER = "root";
  private static final String PASSWORD = "";
  
```
* Clean Maven
```bash
  mvn clean
  mvn package -f pom.xml
```
* Generate war
```bash
  mvn package -f pom.xml
```
* Run the war in Tom EE
```bash
  mvn tomee:run -f pom.xml
```

## Using the application
* While running for the first time, you might see like **Building front-end development bundle**. Please wait for the process finish. It should take few mins.
* By default application creates a user login. You can use it to login to the application.
```text
  Username: implementer.local
  Password: implementer
```


