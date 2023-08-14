package com.eastnetic.application.weathers.service;

import com.eastnetic.application.weathers.entity.WeatherData;

import java.time.LocalDate;

public interface WeatherProviderService {

    WeatherData getDailyWeatherData(double latitude, double longitude, String timezone);

    WeatherData getHourlyWeatherDataOfADay(double latitude, double longitude, String timezone, LocalDate date);

}
