package com.example.application.service;

import com.example.application.data.dto.LocationDto;
import com.example.application.data.dto.meteo.WeatherForecastResponse;

import java.util.List;

public interface OpenMeteoService {
    List<LocationDto> getLocations(String query);
    WeatherForecastResponse getWeatherData(double lat, double lang, String timezone);
}
