package com.hiddenhopes.WeatherApp.model;

import java.util.List;

public class HourlyData {
    private List<String> time;
    private List<Double> temperature_2m;
    private List<Double> rain;
    private List<Double> windspeed_10m;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(List<Double> temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public List<Double> getRain() {
        return rain;
    }

    public void setRain(List<Double> rain) {
        this.rain = rain;
    }

    public List<Double> getWindspeed_10m() {
        return windspeed_10m;
    }

    public void setWindspeed_10m(List<Double> windspeed_10m) {
        this.windspeed_10m = windspeed_10m;
    }
}
