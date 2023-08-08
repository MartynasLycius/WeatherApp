package com.example.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class DailyInfo {

    @JsonProperty("time")
    private String[] time;

    @JsonProperty("temperature_2m_max")
    private Double[] temperature2m;

    @JsonProperty("rain_sum")
    private Double[] rain;

    @JsonProperty("windspeed_10m_max")
    private Double[] windspeed10m;

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public Double[] getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(Double[] temperature2m) {
        this.temperature2m = temperature2m;
    }

    public Double[] getRain() {
        return rain;
    }

    public void setRain(Double[] rain) {
        this.rain = rain;
    }

    public Double[] getWindspeed10m() {
        return windspeed10m;
    }

    public void setWindspeed10m(Double[] windspeed10m) {
        this.windspeed10m = windspeed10m;
    }

    @Override
    public String toString() {
        return "HourlyInfo{" +
                "time=" + Arrays.toString(time) +
                ", temperature2m=" + Arrays.toString(temperature2m) +
                ", rain=" + Arrays.toString(rain) +
                ", windspeed10m=" + Arrays.toString(windspeed10m) +
                '}';
    }
}
