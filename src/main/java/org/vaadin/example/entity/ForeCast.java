package org.vaadin.example.entity;

import java.util.List;

public class ForeCast {
    private Location location;
    private String timezone;
    private ForeCastUnit foreCastUnit;
    private List<DailyAndHourlyForecast> dailyAndHourlyForecasts;

    public ForeCast(Location location, String timezone, ForeCastUnit foreCastUnit, List<DailyAndHourlyForecast> dailyAndHourlyForecasts) {
        this.location = location;
        this.timezone = timezone;
        this.foreCastUnit = foreCastUnit;
        this.dailyAndHourlyForecasts = dailyAndHourlyForecasts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public ForeCastUnit getForeCastUnit() {
        return foreCastUnit;
    }

    public void setForeCastUnit(ForeCastUnit foreCastUnit) {
        this.foreCastUnit = foreCastUnit;
    }

    public List<DailyAndHourlyForecast> getDailyAndHourlyForecasts() {
        return dailyAndHourlyForecasts;
    }

    public void setDailyAndHourlyForecasts(List<DailyAndHourlyForecast> dailyAndHourlyForecasts) {
        this.dailyAndHourlyForecasts = dailyAndHourlyForecasts;
    }
}
