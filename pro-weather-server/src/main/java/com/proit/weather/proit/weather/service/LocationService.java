package com.proit.weather.proit.weather.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {

    private static final String API_BASE_URL = "https://geocoding-api.open-meteo.com/v1/";

    public String getLocationData(String cityName) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_BASE_URL + "search?name=" + cityName + "&count=" +100;
        return restTemplate.getForObject(url, String.class);
    }
}
