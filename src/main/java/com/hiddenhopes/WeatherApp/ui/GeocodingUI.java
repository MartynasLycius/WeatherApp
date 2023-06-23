package com.hiddenhopes.WeatherApp.ui;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiddenhopes.WeatherApp.Constant;
import com.hiddenhopes.WeatherApp.model.WeeklyData;
import com.hiddenhopes.WeatherApp.model.Location;
import com.hiddenhopes.WeatherApp.model.DailyData;
import com.hiddenhopes.WeatherApp.model.WeatherForecast;
import com.hiddenhopes.WeatherApp.service.LocationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Route("")
public class GeocodingUI extends VerticalLayout {

    private LocationService geocodingService;

    private Grid<Location> grid;
    private TextField searchField;

    @Autowired
    public GeocodingUI(LocationService geocodingService) {
        this.geocodingService = geocodingService;

        searchField = new TextField("Search");
        searchField.setPlaceholder("Enter location name");
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> searchLocation(event.getValue()));

        grid = new Grid<>(Location.class);
        grid.setColumns("name", "latitude", "longitude");
        grid.setSizeFull();

        // Register a selection listener on the grid
        grid.addSelectionListener(event -> {
            if (!event.getFirstSelectedItem().isEmpty()) {
                Location selectedLocation = event.getFirstSelectedItem().get();
                openLocationDetailsPopup(selectedLocation);
            }
        });

        add(searchField, grid);
        setWidth("100%");
        setHeight("100%");

        refreshGrid();
    }

    private void searchLocation(String name) {
        refreshGrid(name);
    }

    private void refreshGrid() {
        refreshGrid(null);
    }

    private void refreshGrid(String name) {
        List<Location> results;
        if (name != null && !name.isEmpty()) {
            results = geocodingService.searchLocations(name);
        } else {
            results = geocodingService.searchLocations("your_location_name");
        }
        grid.setItems(results);
    }

    private void openLocationDetailsPopup(Location location) {
        // Create a dialog
        Dialog dialog = new Dialog();
        dialog.setWidth("80%");
        dialog.setHeight("70%");

        // Make the API call and get the response
        WeatherForecast weatherForecast = makeApiCall(location.getLatitude(), location.getLongitude());
        WeeklyData dailyData = weatherForecast.getDaily();
        // Create a grid to display the hourly weather data
        Grid<DailyData> weatherGrid = new Grid<>(DailyData.class);
        weatherGrid.setColumns("time", "temperature2mMax", "temperature2mMin", "rainSum", "windspeed10mMax");
        weatherGrid.setItems(createWeatherDataList(dailyData.getTime(), dailyData.getTemperature2mMax(), dailyData.getTemperature2mMin(), dailyData.getRainSum(), dailyData.getWindspeed10mMax()));

        // Add a close button to the dialog
        Button closeButton = new Button("Close");
        closeButton.getElement().getStyle().set("position", "absolute");
        closeButton.getElement().getStyle().set("top", "0");
        closeButton.getElement().getStyle().set("right", "0");
        closeButton.addClickListener(event -> dialog.close());

        // Add the weather grid and close button to the dialog
        VerticalLayout dialogContent = new VerticalLayout(weatherGrid, closeButton);
        dialog.add(dialogContent);

        // Open the dialog
        dialog.open();
    }
    private WeatherForecast makeApiCall(double latitude, double longitude) {
        try {
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + Constant.DAILY_PARAMS;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                ObjectMapper objectMapper = new ObjectMapper();
                WeatherForecast weatherForecast = objectMapper.readValue(response.toString(), WeatherForecast.class);

                return weatherForecast;
            } else {
                // Handle error response
                return null;
            }
        } catch (Exception e) {
            // Handle exception
            return null;
        }
    }

    private List<DailyData> createWeatherDataList(List<String> time, List<Double> temperature_2m_max, List<Double> temperature_2m_min,
                                                  List<Double> rain, List<Double> windspeed_10m) {
        List<DailyData> weatherDataList = new ArrayList<>();

        int itemCount = Math.min(time.size(), Math.min(temperature_2m_max.size(), Math.min(temperature_2m_min.size(), Math.min(rain.size(), windspeed_10m.size()))));

        for (int i = 0; i < itemCount; i++) {
            DailyData weatherData = new DailyData();
            weatherData.setTime(time.get(i));
            weatherData.setTemperature2mMax(temperature_2m_max.get(i));
            weatherData.setTemperature2mMin(temperature_2m_min.get(i));
            weatherData.setRainSum(rain.get(i));
            weatherData.setWindspeed10mMax(windspeed_10m.get(i));
            weatherDataList.add(weatherData);
        }

        return weatherDataList;
    }

    private List<String> convertDoubleListToString(List<Double> doubleList) {
        List<String> stringList = new ArrayList<>();
        for (Double value : doubleList) {
            stringList.add(String.format("%.2f", value));
        }
        return stringList;
    }
}
