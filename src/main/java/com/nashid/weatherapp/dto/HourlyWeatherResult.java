package com.nashid.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyWeatherResult {
    private Double elevation;
    private WeatherUnit hourly_units;
    private HourlyWeather hourly;

    public HourlyWeatherResult() {
    }

    public HourlyWeatherResult(Double elevation, WeatherUnit hourly_units, HourlyWeather hourly) {
        this.elevation = elevation;
        this.hourly_units = hourly_units;
        this.hourly = hourly;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public WeatherUnit getHourly_units() {
        return hourly_units;
    }

    public void setHourly_units(WeatherUnit hourly_units) {
        this.hourly_units = hourly_units;
    }

    public HourlyWeather getHourly() {
        return hourly;
    }

    public void setHourly(HourlyWeather hourly) {
        this.hourly = hourly;
    }
}
