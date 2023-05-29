package com.example.application.dto;

public class DailyUnits {
    private String time;
    private String temperature_2m_max;
    private String temperature_2m_min;
    private String rain_sum;
    private String windspeed_10m_max;

    public DailyUnits() {
    }

    public DailyUnits(String time, String temperature_2m_max, String temperature_2m_min, String rain_sum, String windspeed_10m_max) {
        this.time = time;
        this.temperature_2m_max = temperature_2m_max;
        this.temperature_2m_min = temperature_2m_min;
        this.rain_sum = rain_sum;
        this.windspeed_10m_max = windspeed_10m_max;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(String temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public String getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(String temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public String getRain_sum() {
        return rain_sum;
    }

    public void setRain_sum(String rain_sum) {
        this.rain_sum = rain_sum;
    }

    public String getWindspeed_10m_max() {
        return windspeed_10m_max;
    }

    public void setWindspeed_10m_max(String windspeed_10m_max) {
        this.windspeed_10m_max = windspeed_10m_max;
    }
}
