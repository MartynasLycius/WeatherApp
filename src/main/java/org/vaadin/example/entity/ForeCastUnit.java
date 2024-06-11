package org.vaadin.example.entity;

public class ForeCastUnit {
    private String time;
    private String temperature_2m;
    private String temperature_2m_max;
    private String temperature_2m_min;
    private String rain;
    private String rain_sum;
    private String wind_speed_10m;
    private String wind_speed_10m_max;

    public ForeCastUnit(String time, String temperature_2m, String rain, String wind_speed_10m) {
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.rain = rain;
        this.wind_speed_10m = wind_speed_10m;
    }

    public ForeCastUnit(String time, String temperature_2m_max, String temperature_2m_min, String rain_sum, String wind_speed_10m_max) {
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

    public String getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(String temperature_2m) {
        this.temperature_2m = temperature_2m;
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

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getRain_sum() {
        return rain_sum;
    }

    public void setRain_sum(String rain_sum) {
        this.rain_sum = rain_sum;
    }

    public String getWind_speed_10m() {
        return wind_speed_10m;
    }

    public void setWind_speed_10m(String wind_speed_10m) {
        this.wind_speed_10m = wind_speed_10m;
    }

    public String getWind_speed_10m_max() {
        return wind_speed_10m_max;
    }

    public void setWind_speed_10m_max(String wind_speed_10m_max) {
        this.wind_speed_10m_max = wind_speed_10m_max;
    }
}
