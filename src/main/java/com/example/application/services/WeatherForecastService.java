package com.example.application.services;

import com.example.application.dto.DailyForecast;
import com.example.application.dto.ForecastResponse;
import com.example.application.dto.HourlyForecast;

import java.util.List;

public interface WeatherForecastService {

    ForecastResponse getWeatherForecastForLocation(String latitude, String longitude, String tz);

    List<DailyForecast> getDailyForeCast(ForecastResponse forecastResponse);

    List<HourlyForecast> getHourlyForeCast(ForecastResponse forecastResponse, String date);
}
