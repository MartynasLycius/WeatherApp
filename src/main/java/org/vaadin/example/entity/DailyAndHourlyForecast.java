package org.vaadin.example.entity;

public class DailyAndHourlyForecast {
    private String time;
    private double temperature_2m;
    private double temperature_2m_max;
    private double temperature_2m_min;
    private double rain;
    private double rain_sum;
    private double wind_speed_10m;
    private double wind_speed_10m_max;

    public DailyAndHourlyForecast(String time, double temperature_2m, double rain, double wind_speed_10m) {
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.rain = rain;
        this.wind_speed_10m = wind_speed_10m;
    }

    public DailyAndHourlyForecast(String time, double temperature_2m_max, double temperature_2m_min, double rain_sum, double wind_speed_10m_max) {
        this.time = time;
        this.temperature_2m_max = temperature_2m_max;
        this.temperature_2m_min = temperature_2m_min;
        this.rain_sum = rain_sum;
        this.wind_speed_10m_max = wind_speed_10m_max;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(double temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public double getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(double temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public double getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(double temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getRain_sum() {
        return rain_sum;
    }

    public void setRain_sum(double rain_sum) {
        this.rain_sum = rain_sum;
    }

    public double getWind_speed_10m() {
        return wind_speed_10m;
    }

    public void setWind_speed_10m(double wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }

    public double getWind_speed_10m_max() {
        return wind_speed_10m_max;
    }

    public void setWind_speed_10m_max(double wind_speed_10m_max) {
        this.wind_speed_10m_max = wind_speed_10m_max;
    }

    @Override
    public String toString() {
        return "DailyAndHourlyForecast{" +
                "time='" + time + '\'' +
                ", temperature_2m_max=" + temperature_2m_max +
                ", temperature_2m_min=" + temperature_2m_min +
                ", rain_sum=" + rain_sum +
                ", wind_speed_10m_max=" + wind_speed_10m_max +
                '}';
    }
}
