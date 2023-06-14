package com.example.application.service;

import com.example.application.data.dto.LocationDto;
import com.example.application.data.dto.meteo.WeatherForecastResponse;

import java.util.List;

public interface WeatherDataService {

    List<LocationDto> getLocations(String value);

    WeatherForecastResponse getWeatherData(double latitude, double longitude);
}
