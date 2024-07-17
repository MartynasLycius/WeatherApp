package org.weather.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.weather.app.models.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class CurrentForecastService extends BaseService<CurrentForecastCustom>{

    private static final String API_URL_TEMPLATE = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,rain,showers,snowfall,weather_code,cloud_cover,surface_pressure,wind_speed_10m&hourly=rain&daily=weather_code,temperature_2m_max,temperature_2m_min,uv_index_clear_sky_max,rain_sum,showers_sum,wind_speed_10m_max&timezone=auto&models=gem_global";

    public CompletableFuture<CurrentForecastCustom> fetchLocationDetails(String latitude, String longitude) {
        String apiUrl = String.format(API_URL_TEMPLATE, latitude, longitude);
        return fetch(apiUrl, this::parseLocationDetails);
    }

    private CurrentForecastCustom parseLocationDetails(String response){
        List<HourlyRain> hourlyRains = parseHourlyRain(response);
        List<DailyForecast> dailyForecasts = parseDailyForecast(response);
        CurrentWeather currentWeather = parseCurrentWeather(response);
        return new CurrentForecastCustom(currentWeather, dailyForecasts, hourlyRains);
    }

    private List<DailyForecast> parseDailyForecast(String responseBody) {
        ObjectMapper mapper = new ObjectMapper();
        List<DailyForecast> dailyForecastList = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(responseBody);
            JsonNode daily = root.path("daily");
            JsonNode times = daily.path("time");
            JsonNode maxTemperatures = daily.path("temperature_2m_max");
            JsonNode minTemperatures = daily.path("temperature_2m_min");
            JsonNode weatherCodes = daily.path("weather_code");

            for (int i = 0; i < times.size(); i++) {
                DailyForecast forecast = new DailyForecast();
                LocalDate date = LocalDate.parse(times.get(i).asText());
                forecast.setDate(date);
                forecast.setMaxTemperature(maxTemperatures.get(i).asText() + "°C");
                forecast.setMinTemperature(minTemperatures.get(i).asText() + "°C");
                forecast.setWeatherCode(weatherCodes.get(i).asText());

                dailyForecastList.add(forecast);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dailyForecastList;
    }

    private CurrentWeather parseCurrentWeather(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        CurrentWeather currentWeather = new CurrentWeather();

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode current = rootNode.path("current");
            currentWeather.setTemperature(current.get("temperature_2m").asText());
            currentWeather.setApparentTemperature(current.get("apparent_temperature").asText());
            currentWeather.setWindSpeed(current.get("wind_speed_10m").asText());
            currentWeather.setHumidity(current.get("relative_humidity_2m").asText());
            currentWeather.setSurfacePressure(current.get("surface_pressure").asText());
            currentWeather.setWeatherCode(current.get("weather_code").asText());
            currentWeather.setPrecipitation(current.get("precipitation").asText());
            currentWeather.setRain(current.get("rain").asText());
            currentWeather.setTotalCloudCover(current.get("cloud_cover").asText());
            currentWeather.setLastUpdateTime(current.get("time").asText());
            currentWeather.setIsDay(current.get("is_day").asText());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return currentWeather;
    }

    private List<HourlyRain> parseHourlyRain(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<HourlyRain> hourlyRainList = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode currentNode = rootNode.path("current");
            JsonNode hourlyNode = rootNode.path("hourly");
            JsonNode timeNodes = hourlyNode.path("time");
            JsonNode rainNodes = hourlyNode.path("rain");

            String currentDateString = currentNode.get("time").asText();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime localDate = LocalDateTime.parse(currentDateString, inputFormatter);


            for (int i = 0; i < timeNodes.size(); i++) {
                String time = timeNodes.get(i).asText();
                double rain = rainNodes.get(i).asDouble();
                if (localDate.isBefore(LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME))) {
                    HourlyRain hourlyRain = new HourlyRain(time, rain);
                    hourlyRainList.add(hourlyRain);
                    if (hourlyRainList.size() == 24) {
                        break;
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return hourlyRainList;
    }
}
