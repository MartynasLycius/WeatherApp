package com.nashid.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherUnit {
    private String time;
    private String temperature_2m;
    private String wind_speed_10m;
    private String rain;

    public WeatherUnit() {
    }

    public WeatherUnit(String time, String temperature_2m, String wind_speed_10m, String rain) {
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.wind_speed_10m = wind_speed_10m;
        this.rain = rain;
    }

    public String getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(String temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public String getWind_speed_10m() {
        return wind_speed_10m;
    }

    public void setWind_speed_10m(String wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
