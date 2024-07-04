package com.nashid.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nashid.weatherapp.core.utils.UnixTimestampDeserializer;

import java.time.Instant;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeather {
    @JsonDeserialize(using = UnixTimestampDeserializer.class)
    private String time;
    private Double temperature_2m;
    private Double wind_speed_10m;
    private Double rain;
    private Integer weather_code;

    public CurrentWeather() {
    }

    public CurrentWeather(Integer weather_code, String time, Double temperature_2m, Double wind_speed_10m, Double rain) {
        this.weather_code = weather_code;
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.wind_speed_10m = wind_speed_10m;
        this.rain = rain;
    }

    public Double getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(Double temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public Double getWind_speed_10m() {
        return wind_speed_10m;
    }

    public void setWind_speed_10m(Double wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(Integer weather_code) {
        this.weather_code = weather_code;
    }
}
