# Weather Application

## Application Features:

1. **Authentication System:**
    - Users must be logged in to access other views.

2. **Location List:**
    - Displays a list of locations.
    - Users can add locations to their favorite list by clicking a button.

3. **Favorite List:**
    - Users can view their favorite locations on a separate page.

4. **Daily Weather Forecast:**
    - Accessible from the location list and favorite list pages.
    - Provides a daily weather forecast.

5. **Hourly Weather Forecast:**
    - Users can navigate to this view from the daily weather forecast page.
    - Displays hourly weather forecasts.

## Used Technology

- **Vaadin 24.3.13:**
   - Based on Jakarta EE 10.0, requires Java 17.

- **MySQL Database**


## Running the Application

1. **Import the Project:**
   - Open your preferred IDE.
   - Import the project as a Maven project.

2. **Database Setup:**
   - Execute the `database.sql` file from the project root directory in your MySQL server.
   - This file contains the necessary database schema and initial data.

3. **Run the Application:**
   - Once the project is imported and the database is set up, run the application using
   ```
   mvn package tomee:run
   ```
   Browse application using [http://localhost:8080](http://localhost:8080)