package com.example.application.dto;

public class CurrentWeather {
    private double temperature;
    private double windspeed;
    private double winddirection;
    private int weathercode;
    private int is_day;
    private String time;

    public CurrentWeather() {
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(double winddirection) {
        this.winddirection = winddirection;
    }

    public int getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(int weathercode) {
        this.weathercode = weathercode;
    }

    public int getIs_day() {
        return is_day;
    }

    public void setIs_day(int is_day) {
        this.is_day = is_day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public CurrentWeather(double temperature, double windspeed, double winddirection, int weathercode, int is_day, String time) {
        this.temperature = temperature;
        this.windspeed = windspeed;
        this.winddirection = winddirection;
        this.weathercode = weathercode;
        this.is_day = is_day;
        this.time = time;
    }
}
