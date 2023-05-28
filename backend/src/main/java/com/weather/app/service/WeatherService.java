package com.weather.app.service;

import com.weather.app.model.DailyWeatherResponseModel;
import com.weather.app.model.HourlyWeatherResponseModel;
import org.springframework.http.ResponseEntity;

public interface WeatherService {
    ResponseEntity<DailyWeatherResponseModel> getDailyWeather(String timezone, String latitude, String longitude);

    ResponseEntity<HourlyWeatherResponseModel> getHourlyWeather(String latitude, String longitude);
}
