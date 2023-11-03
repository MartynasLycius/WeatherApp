package com.example.application.data;

import com.jsonparsing.Json;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class ForecastApiService
{

    public void fetchAndStoreForecasts(Location location)
    {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + location.getLatitude() +
                "&longitude=" + location.getLongitude() +
                "&hourly=temperature_2m,rain,windspeed_10m" +
                "&daily=temperature_2m_max,temperature_2m_min" +
                "&timezone=" + location.getTimezone();

        String responseBody = restTemplate.getForObject(apiUrl, String.class);

        try
        {
            JsonNode rootNode = Json.parse(responseBody); // parse JSON response
            JsonNode dailyNode = rootNode.get("daily");
            JsonNode hourlyNode = rootNode.get("hourly");

            List<DailyForecast> dailyForecasts = parseDailyForecasts(dailyNode);
            List<HourlyForecast> hourlyForecasts = parseHourlyForecasts(hourlyNode);

            location.setDailyForecasts(dailyForecasts);
            location.setHourlyForecasts(hourlyForecasts);

        } catch (Exception e)
        {
            e.printStackTrace(); // better to handle exception properly, log or throw
        }
    }


    private List<DailyForecast> parseDailyForecasts(JsonNode dailyNode)
    {
        List<DailyForecast> dailyForecasts = new ArrayList<>();
        if (dailyNode != null && dailyNode.isObject())
        {
            JsonNode timeNode = dailyNode.get("time");
            JsonNode maxTempNode = dailyNode.get("temperature_2m_max");
            JsonNode minTempNode = dailyNode.get("temperature_2m_min");

            if (timeNode != null && maxTempNode != null && minTempNode != null)
            {
                for (int i = 0; i < timeNode.size(); i++)
                {
                    String date = timeNode.get(i).asText();
                    double maxTemp = maxTempNode.get(i).asDouble();
                    double minTemp = minTempNode.get(i).asDouble();

                    DailyForecast forecast = new DailyForecast(date, maxTemp, minTemp);
                    dailyForecasts.add(forecast);
                }
            }
        }
        return dailyForecasts;
    }

    private List<HourlyForecast> parseHourlyForecasts(JsonNode hourlyNode)
    {
        List<HourlyForecast> hourlyForecasts = new ArrayList<>();
        if (hourlyNode != null && hourlyNode.isObject())
        {
            JsonNode timeNode = hourlyNode.get("time");
            JsonNode temperatureNode = hourlyNode.get("temperature_2m");
            JsonNode rainNode = hourlyNode.get("rain");
            JsonNode windSpeedNode = hourlyNode.get("windspeed_10m");

            if (timeNode != null && temperatureNode != null && rainNode != null && windSpeedNode != null)
            {
                for (int i = 0; i < timeNode.size(); i++)
                {
                    String time = timeNode.get(i).asText();
                    String temperature = temperatureNode.get(i).asText();
                    String rain = rainNode.get(i).asText();
                    String windSpeed = windSpeedNode.get(i).asText();

                    HourlyForecast forecast = new HourlyForecast(time, temperature, rain, windSpeed);
                    hourlyForecasts.add(forecast);
                }
            }
        }
        return hourlyForecasts;
    }


}
