package com.example.application.dto;

public class HourlyForecast {
    private String hour;
    private Double temperature;
    private Double rain;
    private Double windSpeed;

    public HourlyForecast() {
    }

    public HourlyForecast(String hour, Double temperature, Double rain, Double windSpeed) {
        this.hour = hour;
        this.temperature = temperature;
        this.rain = rain;
        this.windSpeed = windSpeed;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
