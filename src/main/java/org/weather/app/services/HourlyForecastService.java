package org.weather.app.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.weather.app.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class HourlyForecastService extends BaseService<List<HourlyForecast>>{

    private static final String API_URL_TEMPLATE = "https://api.open-meteo.com/v1/forecast?latitude=%s4&longitude=%s&hourly=temperature_2m,relative_humidity_2m,dew_point_2m,apparent_temperature,rain,weather_code,surface_pressure,cloud_cover,snowfall,wind_speed_10m,wind_direction_10m&timezone=auto&start_date=%s&end_date=%s&models=gem_global";

    public CompletableFuture<List<HourlyForecast>> fetchHourlyForecast(String latitude, String longitude, String dateFrom, String dateTo) {
        String apiUrl = String.format(API_URL_TEMPLATE, latitude, longitude, dateFrom, dateTo);
        return fetch(apiUrl, this::parseHourlyForecast);
    }

    private List<HourlyForecast> parseHourlyForecast(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        List<HourlyForecast> hourlyForecastList = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(responseBody);
            JsonNode hourly = root.path("hourly");
            JsonNode times = hourly.path("time");
            JsonNode temperatures = hourly.path("temperature_2m");
            JsonNode humidities = hourly.path("relative_humidity_2m");
            JsonNode dewPoints = hourly.path("dew_point_2m");
            JsonNode rains = hourly.path("rain");
            JsonNode weatherCodes = hourly.path("weather_code");
            JsonNode windSpeeds = hourly.path("wind_speed_10m");
            JsonNode windDirections = hourly.path("wind_direction_10m");
            JsonNode snowFalls = hourly.path("snowfall");

            for (int i = 0; i < times.size(); i++) {
                HourlyForecast forecast = new HourlyForecast();
                forecast.setTime(times.get(i).asText());
                forecast.setTemperature(temperatures.get(i).asText());
                forecast.setHumidity(humidities.get(i).asText());
                forecast.setDewPoint(dewPoints.get(i).asText());
                forecast.setRain(rains.get(i).asText());
                forecast.setWeather(weatherCodes.get(i).asText());
                forecast.setWindSpeed(windSpeeds.get(i).asText());
                forecast.setWindDirection(windDirections.get(i).asText());
                forecast.setSnowfall(snowFalls.get(i).asText());
                hourlyForecastList.add(forecast);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hourlyForecastList;
    }



}
