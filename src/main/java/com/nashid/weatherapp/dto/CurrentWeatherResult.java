package com.nashid.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherResult {
    private Double elevation;
    private WeatherUnit current_units;
    private CurrentWeather current;

    public CurrentWeatherResult() {
    }

    public CurrentWeatherResult(Double elevation, WeatherUnit current_units, CurrentWeather current) {
        this.elevation = elevation;
        this.current_units = current_units;
        this.current = current;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public WeatherUnit getCurrent_units() {
        return current_units;
    }

    public void setCurrent_units(WeatherUnit current_units) {
        this.current_units = current_units;
    }

    public CurrentWeather getCurrent() {
        return current;
    }

    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }
}
