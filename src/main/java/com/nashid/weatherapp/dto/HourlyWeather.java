package com.nashid.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nashid.weatherapp.core.utils.UnixTimestampListDeserializer;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyWeather {
    @JsonDeserialize(using = UnixTimestampListDeserializer.class)
    private List<Date> time;
    private List<Double> temperature_2m;
    private List<Double> wind_speed_10m;
    private List<Double> rain;

    private List<Integer> weather_code;

    public HourlyWeather() {
    }

    public HourlyWeather(List<Integer> weather_code, List<Date> time, List<Double> temperature_2m, List<Double> wind_speed_10m, List<Double> rain) {
        this.weather_code = weather_code;
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.wind_speed_10m = wind_speed_10m;
        this.rain = rain;
    }

    public List<Double> getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(List<Double> temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public List<Double> getWind_speed_10m() {
        return wind_speed_10m;
    }

    public void setWind_speed_10m(List<Double> wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }

    public List<Double> getRain() {
        return rain;
    }

    public void setRain(List<Double> rain) {
        this.rain = rain;
    }

    public List<Date> getTime() {
        return time;
    }

    public void setTime(List<Date> time) {
        this.time = time;
    }

    public List<Integer> getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(List<Integer> weather_code) {
        this.weather_code = weather_code;
    }
}
