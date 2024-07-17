package org.weather.app.models;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DailyForecast {
    private LocalDate date;
    private String dayName;
    private String maxTemperature;
    private String minTemperature;
    private String weatherCode;

    // Getters and setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public String getDayName() {
        return dayName;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    @Override
    public String toString() {
        return "DailyForecast{" +
                "date=" + date +
                ", dayName='" + dayName + '\'' +
                ", maxTemperature='" + maxTemperature + '\'' +
                ", minTemperature='" + minTemperature + '\'' +
                ", weatherCode='" + weatherCode + '\'' +
                '}';
    }
}

