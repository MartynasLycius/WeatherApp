# Weather App
## Requirement from : https://github.com/MartynasLycius/WeatherApp 
### How to Run this project

1. Clone the project from root by clicking code button.
2. Copy https/ssh link (https://github.com/tanmoy069/Weather-App.git / git@github.com:tanmoy069/Weather-App.git)
3. Add a clone repository on your inteliji / eclipse
4. Then import your git repository to local (it will take time to download dependencies)
5. Create a mysql database (db name can be anything)
6. Change the db name in application.properties
7. Run the project as Spring Boot App
8. Table will be created automatically.
9. Download the city list csv file from : https://simplemaps.com/data/world-cities
10. Import your csv file in your local db. Then execute the following query, 
* insert into cities (city_name, latitude, longitude, country_name)
  select city, lat, lng, country from worldcities w; // here worldcitites table name is your imported table name.

### Project Description

#### A project of weather api integration from https://open-meteo.com/
1. First login to the system using username: admin/user and password same as username.
2. After successful login you find a combo box of location list on top of the page under main layout.
3. To see location wise weather information select a location from combo box or find a location in combo box.
4. Then select your prefered location and see location and current weather details.
5. To see hourly basis details of selected location select "Show hourl details" button.
6. To hide the hourly basis details select "Hide details" button.

###### N.B : Due to my lack of experience in frontend technologies, I unable to  fullfill the frontend requirements. Hopefully I can overcome my limitations on frontend.

# 
#### Thank you
##### Tanmoy Tushar
###### If you have any suggestion, give me feedback.
