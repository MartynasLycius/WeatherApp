package com.sheikhimtiaz.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheikhimtiaz.application.model.WeatherData;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class WeatherService {

    private ObjectMapper objectMapper = new ObjectMapper();

    public WeatherData getWeather(double latitude, double longitude) throws IOException {
        return getWeatherData(latitude, longitude);
    }

    private WeatherData getWeatherData(double latitude, double longitude) throws IOException {
        String apiUrl = String.format("https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&hourly=temperature_2m,windspeed_10m,precipitation&daily=temperature_2m_max,temperature_2m_min", latitude, longitude);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JsonNode rootNode = objectMapper.readTree(inline.toString());
            WeatherData weatherData = new WeatherData();

            // Parse daily data
            JsonNode dailyNode = rootNode.path("daily");
            weatherData.setDailyTemperatureMax(parseDoubleList(dailyNode.path("temperature_2m_max")));
            weatherData.setDailyTemperatureMin(parseDoubleList(dailyNode.path("temperature_2m_min")));

            // Parse hourly data
            JsonNode hourlyNode = rootNode.path("hourly");
            weatherData.setHourlyTime(parseStringList(hourlyNode.path("time")));
            weatherData.setHourlyTemperature(parseDoubleList(hourlyNode.path("temperature_2m")));
            weatherData.setHourlyWindspeed(parseDoubleList(hourlyNode.path("windspeed_10m")));
            weatherData.setHourlyPrecipitation(parseDoubleList(hourlyNode.path("precipitation")));

            return weatherData;
        }
    }

    private List<Double> parseDoubleList(JsonNode jsonNode) {
        List<Double> list = new ArrayList<>();
        jsonNode.forEach(node -> list.add(node.asDouble()));
        return list;
    }

    private List<String> parseStringList(JsonNode jsonNode) {
        List<String> list = new ArrayList<>();
        jsonNode.forEach(node -> list.add(node.asText()));
        return list;
    }
}
