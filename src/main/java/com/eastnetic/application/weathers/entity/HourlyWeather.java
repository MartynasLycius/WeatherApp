package com.eastnetic.application.weathers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HourlyWeather {

    @JsonProperty("time")
    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperature;

    @JsonProperty("rain")
    private List<Double> rain;

    @JsonProperty("windspeed_10m")
    private List<Double> windSpeed;

    @JsonProperty("winddirection")
    private List<Integer> windDirection;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Double> temperature) {
        this.temperature = temperature;
    }

    public List<Double> getRain() {
        return rain;
    }

    public void setRain(List<Double> rain) {
        this.rain = rain;
    }

    public List<Double> getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(List<Double> windSpeed) {
        this.windSpeed = windSpeed;
    }

    public List<Integer> getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(List<Integer> windDirection) {
        this.windDirection = windDirection;
    }
}
