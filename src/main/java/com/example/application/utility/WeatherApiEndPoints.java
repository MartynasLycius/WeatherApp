package com.example.application.utility;

public class WeatherApiEndPoints {
    public static final String METEO_GEOCODING_API_URL = "https://geocoding-api.open-meteo.com" +
            "/v1/search?name=%s&count=100&language=en&format=json";

    public static final String METEO_FORECAST_API_URL = "https://api.open-meteo.com/v1/forecast?" +
                    "latitude=%s" +
                    "&longitude=%s" +
                    "&hourly=temperature_2m,rain,windspeed_10m" +
                    "&daily=temperature_2m_max,temperature_2m_min,rain_sum,windspeed_10m_max&current_weather=true" +
                    "&timezone=%s";
}
