package com.hiddenhopes.WeatherApp.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiddenhopes.WeatherApp.Constant;
import com.hiddenhopes.WeatherApp.model.*;
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
import java.util.stream.Collectors;

@Route("")
public class GeocodingUI extends VerticalLayout {

    private final LocationService geocodingService;

    private final Grid<Location> grid;
    private final TextField searchField;

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
                openLocationWeatherDetailsDailyPopup(selectedLocation);
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

    private void openLocationWeatherDetailsDailyPopup(Location location) {
        // Create a dialog
        Dialog dailyWeatherDialog = new Dialog();
        dailyWeatherDialog.setWidth("80%");
        dailyWeatherDialog.setHeight("70%");

        // Make the API call and get the response
        WeatherForecast weatherForecast = makeApiCall(location.getLatitude(), location.getLongitude(), Constant.DAILY_PARAMS);
        WeeklyData weeklyData = weatherForecast.getDaily();
        // Create a grid to display the hourly weather data
        Grid<DailyData> dailyWeatherGrid = new Grid<>(DailyData.class);
        dailyWeatherGrid.setColumns("time", "temperature2mMax", "temperature2mMin", "rainSum", "windspeed10mMax");
        dailyWeatherGrid.setItems(createWeatherDailyDataList(weeklyData.getTime(), weeklyData.getTemperature2mMax(), weeklyData.getTemperature2mMin(), weeklyData.getRainSum(), weeklyData.getWindspeed10mMax()));

        // Register a selection listener on the grid
        dailyWeatherGrid.addSelectionListener(event -> {
            if (!event.getFirstSelectedItem().isEmpty()) {
                DailyData selectedDay = event.getFirstSelectedItem().get();
                openLocationWeatherDetailsHourlyPopup(selectedDay.getTime(), location.getLatitude(), location.getLongitude());
            }
        });

        // Add a close button to the dialog
        Button closeButton = new Button("Close");
        closeButton.getElement().getStyle().set("position", "absolute");
        closeButton.getElement().getStyle().set("top", "0");
        closeButton.getElement().getStyle().set("right", "0");
        closeButton.addClickListener(event -> dailyWeatherDialog.close());

        // Add the weather grid and close button to the dialog
        VerticalLayout dialogContent = new VerticalLayout(dailyWeatherGrid, closeButton);
        dailyWeatherDialog.add(dialogContent);

        // Open the dialog
        dailyWeatherDialog.open();
    }

    private void openLocationWeatherDetailsHourlyPopup(String date, double latitude, double longitude) {
        // Create a dialog
        Dialog hourlyWeatherDialog = new Dialog();
        hourlyWeatherDialog.setWidth("60%");
        hourlyWeatherDialog.setHeight("60%");

        // Make the API call and get the response
        WeatherForecast weatherForecast = makeApiCall(latitude, longitude, Constant.HOURLY_PARAMS);
        HourlyData data = weatherForecast.getHourly();
        // Create a grid to display the hourly weather data
        Grid<SingleHourData> hourlyWeatherGrid = new Grid<>(SingleHourData.class);
        hourlyWeatherGrid.setColumns("time", "temperature2m", "rain", "windspeed10m");
        List<SingleHourData> hourlyDataList = createWeatherHourlyDataList(data.getTime(), data.getTemperature_2m(), data.getRain(), data.getWindspeed_10m());
        List<SingleHourData> filteredList = hourlyDataList.stream().filter(o -> o.getTime().startsWith(date)).collect(Collectors.toList());
        filteredList.forEach(e -> e.setTime(e.getTime().substring(11)));
        hourlyWeatherGrid.setItems(filteredList);

        // Add a close button to the dialog
        Button closeButton = new Button("Close");
        closeButton.getElement().getStyle().set("position", "absolute");
        closeButton.getElement().getStyle().set("top", "0");
        closeButton.getElement().getStyle().set("right", "0");
        closeButton.addClickListener(event -> hourlyWeatherDialog.close());

        // Add the weather grid and close button to the dialog
        VerticalLayout dialogContent = new VerticalLayout(hourlyWeatherGrid, closeButton);
        hourlyWeatherDialog.add(dialogContent);

        // Open the dialog
        hourlyWeatherDialog.open();
    }

    private WeatherForecast makeApiCall(double latitude, double longitude, String params) {
        try {
            String apiUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + params;
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

    private List<DailyData> createWeatherDailyDataList(List<String> time, List<Double> temperature_2m_max, List<Double> temperature_2m_min,
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

    private List<SingleHourData> createWeatherHourlyDataList(List<String> time, List<Double> temperature2m,
                                                             List<Double> rain, List<Double> windspeed10m) {
        List<SingleHourData> hourDataList = new ArrayList<>();

        int itemCount = Math.min(time.size(), Math.min(temperature2m.size(), Math.min(rain.size(), windspeed10m.size())));

        for (int i = 0; i < itemCount; i++) {
            SingleHourData hourData = new SingleHourData();
            hourData.setTime(time.get(i));
            hourData.setTemperature2m(temperature2m.get(i));
            hourData.setRain(rain.get(i));
            hourData.setWindspeed10m(windspeed10m.get(i));
            hourDataList.add(hourData);
        }

        return hourDataList;
    }
}
