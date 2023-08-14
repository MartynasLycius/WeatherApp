package com.eastnetic.application.weathers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherData {

    @JsonProperty("daily")
    private DailyWeather dailyWeather;

    @JsonProperty("hourly")
    private HourlyWeather hourlyWeather;

    @JsonProperty("current_weather")
    private CurrentWeather currentWeather;

    public WeatherData() {}

    public DailyWeather getDailyWeather() {
        return dailyWeather;
    }

    public void setDailyWeather(DailyWeather dailyWeather) {
        this.dailyWeather = dailyWeather;
    }

    public HourlyWeather getHourlyWeather() {
        return hourlyWeather;
    }

    public void setHourlyWeather(HourlyWeather hourlyWeather) {
        this.hourlyWeather = hourlyWeather;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
