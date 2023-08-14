# Weather Application

This is the job interview task for software developer position

## Technology Used
* Vaadin 24 
* Vaadin Chart (Use Trial licence)
* Java 17
* Spring Boot 3
* Maven 3
* Git 2

## Database Used
* H2 Database (in-memory)

## Login Credential
* username: user
* password: password


* username: admin
* password: password

## User Guidelines

### Login
![00_login](https://github.com/thekowsar/bidding-system/assets/8324420/fbe6e010-11a2-44de-a216-9e606aeee6b4)
### Search Location
![01_search_location](https://github.com/thekowsar/bidding-system/assets/8324420/df540821-6283-4b40-a38b-c9e80c59e4a6)
### Daily Chart
![02_show_daly_chart](https://github.com/thekowsar/bidding-system/assets/8324420/74b021b7-db53-4a08-ada3-aeee44fbccca)
### Hourly Chart
![03_show_hourly_chart](https://github.com/thekowsar/bidding-system/assets/8324420/06d2698c-8532-40fa-b4ee-53162d28d58d)
## What to do
* Fork the repository
* After work is done, make a pull request and notify me by email

## Task description
You need to use the Weather API provided here https://open-meteo.com/en to make the weather application. 
1. Call the API to get the locations by city name. Make the paging available with 10 items per page and a filter to filter by location name.
2. When clicked on the location it should show the weather forecast for the location with the temperature, surface wind and rain hourly. The interface should let you first see the daily forecast and when clicked it then should show the forecast hourly for the day you selected.
3. If you UI contains a chart it is a bonus. The interface should be convenient for user and rely on best usability and design practises
4. Only logged on users should be able to see the weather forecasts.
5. User should be able to mark the location favourite and have the favourite location list for quick access.

All the other specific requirements are up to you

## Technical requirements
* Use  Vaadin (https://vaadin.com/) framework for the frontend. For chart you may use some different framework
* For backend use Java EE
* Use any database (Postgres, Oracle, etc.)
* Make a Maven project

## Main points
* Structure your code
* Use best practises
* Use naming conventions
* Show understanding of software development concepts
