package com.weather.application.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyUnit {
    @JsonProperty("time")
    private String time;

    @JsonProperty("temperature_2m_max")
    private String temperature2m;

    @JsonProperty("rain_sum")
    private String rain;

    @JsonProperty("windspeed_10m_max")
    private String windspeed10m;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(String temperature2m) {
        this.temperature2m = temperature2m;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getWindspeed10m() {
        return windspeed10m;
    }

    public void setWindspeed10m(String windspeed10m) {
        this.windspeed10m = windspeed10m;
    }

    @Override
    public String toString() {
        return "DailyUnit{" +
                "time='" + time + '\'' +
                ", temperature2m=" + temperature2m +
                ", rain=" + rain +
                ", windspeed10m=" + windspeed10m +
                '}';
    }
}
