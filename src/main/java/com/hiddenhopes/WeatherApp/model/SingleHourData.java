package com.hiddenhopes.WeatherApp.model;

public class SingleHourData {
    private String time;
    private Double temperature2m;
    private Double rain;
    private Double windspeed10m;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(Double temperature2m) {
        this.temperature2m = temperature2m;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getWindspeed10m() {
        return windspeed10m;
    }

    public void setWindspeed10m(Double windspeed10m) {
        this.windspeed10m = windspeed10m;
    }
}
