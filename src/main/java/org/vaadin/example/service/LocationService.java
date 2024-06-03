package org.vaadin.example.service;

import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import org.vaadin.example.api.WeatherApiClient;
import org.vaadin.example.model.Location;
import org.vaadin.example.repository.LocationRepository;
import org.vaadin.example.util.Tools;
import org.vaadin.example.view.FavoriteLocationsView;

import java.util.*;
import java.util.logging.Logger;


@CdiComponent
public class LocationService {
    @Inject
    private WeatherApiClient weatherApiClient;
    @Inject
    private LocationRepository locationRepository;
    @Inject
    private UserService userService;

    private static final Logger LOGGER = Logger.getLogger(FavoriteLocationsView.class.getName());

    @Transactional
    public Map saveLocationToFavorites(JsonObject locationData) {
        Map result = new LinkedHashMap();
        try {
            long userId = userService.getLoggedUserId();
            result.put("isSuccess", Boolean.FALSE);
            result.put("isExists", Boolean.FALSE);
            Location location = createLocationFromJson(locationData);
            Optional<Location> existingLocation = locationRepository.findByNameAndCoordinates(
                    userId, location.getName(), location.getLatitude(), location.getLongitude());

            if (existingLocation.isPresent()) {
                result.put("isExists", Boolean.TRUE);
                result.put("message", "Location already exists in favorites list!");
            } else {
                location.setUserId(userId);
                locationRepository.save(location);
                result.put("isSuccess", Boolean.TRUE);
                result.put("message", "Location added to favorites successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("message", "Failed to save location to favorites: " + e.getMessage());
        }
        return result;
    }

    public List<Location> getUserFavoriteLocations(long userId) {
        List<Location> favoriteLocations = locationRepository.findAllLocationsByUserId(userId);
        return favoriteLocations;
    }

    public void deleteFavoriteLocation(long id) {
        locationRepository.deleteFavoriteLocation(id);
    }


    private Location createLocationFromJson(JsonObject locationData) {
        Location location = new Location();
        location.setName(locationData.getString("name"));
        location.setAdmin1(locationData.containsKey("admin1") ? locationData.getString("admin1") : "");
        location.setAdmin2(locationData.containsKey("admin2") ? locationData.getString("admin2") : "");
        location.setAdmin3(locationData.containsKey("admin3") ? locationData.getString("admin3") : "");
        location.setLatitude(locationData.getJsonNumber("latitude").doubleValue());
        location.setLongitude(locationData.getJsonNumber("longitude").doubleValue());
        return location;
    }

    public JsonObject createJsonFromLocation(Location location) {
        return Json.createObjectBuilder()
                .add("name", location.getName())
                .add("admin1", location.getAdmin1())
                .add("admin2", location.getAdmin2())
                .add("admin3", location.getAdmin3())
                .add("latitude", location.getLatitude())
                .add("longitude", location.getLongitude())
                .build();
    }

    public String getLocationString(Location location) {
        StringBuilder locationBuilder = new StringBuilder();
        locationBuilder.append(location.getName());
        if (!location.getAdmin1().isEmpty()) {
            locationBuilder.append(" > ").append(location.getAdmin1());
        }
        if (!location.getAdmin2().isEmpty()) {
            locationBuilder.append(" > ").append(location.getAdmin2());
        }
        if (!location.getAdmin3().isEmpty()) {
            locationBuilder.append(" > ").append(location.getAdmin3());
        }
        return locationBuilder.toString();
    }

    public String getLocationString(JsonObject location) {
        StringBuilder locationBuilder = new StringBuilder();
        locationBuilder.append(location.getString("name").trim());

        if (location.containsKey("admin1") && !location.getString("admin1").isEmpty()) {
            locationBuilder.append(" > ").append(location.getString("admin1").trim());
        }
        if (location.containsKey("admin2") && !location.getString("admin2").isEmpty()) {
            locationBuilder.append(" > ").append(location.getString("admin2").trim());
        }
        if (location.containsKey("admin3") && !location.getString("admin3").isEmpty()) {
            locationBuilder.append(" > ").append(location.getString("admin3").trim());
        }

        return locationBuilder.toString();
    }

    public List<JsonObject> getWeatherForecastApiData(String type, double latitude,double longitude, String date){
        JsonObject dataForecast = null;
        if (type.equals(Tools.WEATHER_FORECAST_TYPE_DAILY)) {
            dataForecast = weatherApiClient.getWeatherForecastDaily(latitude, longitude);
        }else if (type.equals(Tools.WEATHER_FORECAST_TYPE_HOURLY)) {
            dataForecast = weatherApiClient.getWeatherForecastHourly(latitude, longitude, date);
        }
        return processWeatherApiForecastData(type, dataForecast);
    }

    public List<JsonObject> processWeatherApiForecastData(String type, JsonObject dailyForecast) {
        try {
            // Create a list of JsonObjects to hold the tabular data
            List<JsonObject> weatherData = new ArrayList<>();

            if (type.equals(Tools.WEATHER_FORECAST_TYPE_DAILY)) {
                JsonArray timeArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_DAILY).getJsonArray("time");
                JsonArray temperatureMaxArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_DAILY).getJsonArray("temperature_2m_max");
                JsonArray temperatureMinArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_DAILY).getJsonArray("temperature_2m_min");
                JsonArray windSpeedMaxArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_DAILY).getJsonArray("wind_speed_10m_max");
                JsonArray windSpeedMinArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_DAILY).getJsonArray("wind_speed_10m_min");
                JsonArray rainArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_DAILY).getJsonArray("rain_sum");

                // Create a list of JsonObjects to hold the tabular data
                for (int i = 0; i < timeArray.size(); i++) {
                    JsonObject data = Json.createObjectBuilder()
                            .add("time", timeArray.getString(i))
                            .add("temperatureMax", temperatureMaxArray.getJsonNumber(i).doubleValue())
                            .add("temperatureMin", temperatureMinArray.getJsonNumber(i).doubleValue())
                            .add("windSpeedMax", windSpeedMaxArray.getJsonNumber(i).doubleValue())
                            .add("windSpeedMin", windSpeedMinArray.getJsonNumber(i).doubleValue())
                            .add("rain", rainArray.getJsonNumber(i).doubleValue())
                            .build();
                    weatherData.add(data);
                }
            } else if (type.equals(Tools.WEATHER_FORECAST_TYPE_HOURLY)) {
                JsonArray timeArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_HOURLY).getJsonArray("time");
                JsonArray temperatureArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_HOURLY).getJsonArray("temperature_2m");
                JsonArray windSpeedArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_HOURLY).getJsonArray("wind_speed_10m");
                JsonArray rainArray = dailyForecast.getJsonObject(Tools.WEATHER_FORECAST_TYPE_HOURLY).getJsonArray("rain");

                for (int i = 0; i < timeArray.size(); i++) {
                    JsonObject data = Json.createObjectBuilder()
                            .add("time", timeArray.getString(i))
                            .add("temperature", temperatureArray.getJsonNumber(i).doubleValue())
                            .add("windSpeed", windSpeedArray.getJsonNumber(i).doubleValue())
                            .add("rain", rainArray.getJsonNumber(i).doubleValue())
                            .build();
                    weatherData.add(data);
                }
            }
            return weatherData;
        } catch (Exception e) {
            LOGGER.severe("Error displaying daily forecast: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void displayDailyForecast(List<JsonObject> dailyData, double latitude, double longitude, String locName) {
        try {
            // Create a Grid to display the data
            Grid<JsonObject> grid = new Grid<>();
            grid.setItems(dailyData);

            grid.addColumn(data -> data.getString("time")).setHeader("Date");
            grid.addColumn(data ->
                    data.getJsonNumber("temperatureMax").doubleValue() + " ~ " +
                            data.getJsonNumber("temperatureMin").doubleValue()
            ).setHeader("Temperature (°C)");
            grid.addColumn(data ->
                    data.getJsonNumber("windSpeedMax").doubleValue() + " ~ " +
                            data.getJsonNumber("windSpeedMin").doubleValue()
            ).setHeader("Wind Speed (km/h)");
            grid.addColumn(data -> data.getJsonNumber("rain").doubleValue()).setHeader("Rainfall (mm)");

            grid.addItemClickListener(event -> {
                JsonObject location = event.getItem();
                String date = location.getString("time");
                List<JsonObject> hourlyData = getWeatherForecastApiData(Tools.WEATHER_FORECAST_TYPE_HOURLY, latitude, longitude, date);
                displayHourlyForecast(locName, hourlyData);
            });
            grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS,
                    GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

            VerticalLayout layout = new VerticalLayout();
            layout.add(grid);

            displayWeatherForecastDialog(layout,"Daily Weather forecast: " + locName,"950px");

        } catch (Exception e) {
            LOGGER.severe("Error displaying daily forecast: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void displayHourlyForecast(String locName, List<JsonObject> hourlyData) {
        try {
            // Create a Grid to display the data
            Grid<JsonObject> grid = new Grid<>();
            grid.setItems(hourlyData);

            grid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS,
                    GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_ROW_STRIPES);

            grid.addColumn(data -> data.getString("time")).setHeader("Time");
            grid.addColumn(data -> data.getJsonNumber("temperature").doubleValue()).setHeader("Temperature (°C)");
            grid.addColumn(data -> data.getJsonNumber("windSpeed").doubleValue()).setHeader("Wind Speed (km/h)");
            grid.addColumn(data -> data.getJsonNumber("rain").doubleValue()).setHeader("Rainfall (mm)");

            VerticalLayout layout = new VerticalLayout();
            layout.add(grid);

            displayWeatherForecastDialog(layout,"Hourly Weather forecast: " + locName,"800px");

        } catch (Exception e) {
            LOGGER.severe("Error displaying daily forecast: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void displayWeatherForecastDialog(Component layout,String headerTitle,String width){
        Dialog dialogHourly = new Dialog();
        dialogHourly.setCloseOnEsc(true);
        dialogHourly.setCloseOnOutsideClick(true);
        dialogHourly.setWidth(width);
        dialogHourly.setHeaderTitle(headerTitle);
        dialogHourly.add(layout);
        dialogHourly.open();
    }

    public List<JsonObject> getLocationsByCityName(String value, int cnt) {
        return weatherApiClient.getLocationsByCityName(value, cnt);
    }
}

