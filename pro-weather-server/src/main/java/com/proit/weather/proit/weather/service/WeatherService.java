package com.proit.weather.proit.weather.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_BASE_URL = "https://api.open-meteo.com/v1/";

    public String getWeatherData(Float latitude, Float longitude, boolean isHour, String day) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "";
        if(!isHour) {
             url = API_BASE_URL + "forecast?latitude=" + latitude + "&longitude="+longitude+"&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,rain_sum,wind_speed_10m_max&forecast_days=16";

        }else {
             url = API_BASE_URL + "forecast?latitude=" + latitude + "&longitude="+longitude+"&hourly=temperature_2m,rain,wind_speed_10m&start_date="+ day+"&end_date="+day;

        }
        return restTemplate.getForObject(url, String.class);
    }
}
