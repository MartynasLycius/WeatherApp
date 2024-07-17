package org.weather.app.models;

import java.time.format.DateTimeFormatter;

public class HourlyRain extends BaseModel{

    private String time;
    private double rain;
    private String currentTime;

    public HourlyRain(String time, double rain) {
        this.time = convertTime( DateTimeFormatter.ofPattern("hh:mm a"), time);
        this.rain = rain;
    }

    // Getters and setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = convertTime(DateTimeFormatter.ofPattern("HH:mm"), time);
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "HourlyRain{" +
                "time='" + time + '\'' +
                ", rain=" + rain +
                '}';
    }
}
