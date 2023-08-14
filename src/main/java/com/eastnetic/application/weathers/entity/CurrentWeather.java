package com.eastnetic.application.weathers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CurrentWeather {

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("windspeed")
    private double windSpeed;

    @JsonProperty("winddirection")
    private double windDirection;

    @JsonProperty("is_day")
    private boolean isDay;

    @JsonProperty("time")
    private String time;

    public CurrentWeather() {}

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        this.isDay = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
