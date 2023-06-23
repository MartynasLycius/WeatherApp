package com.hiddenhopes.WeatherApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class WeatherForecast {
    private double latitude;
    private double longitude;
    @JsonProperty("generationtime_ms")
    private long generationtimeMs;
    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;
    private String timezone;
    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;
    private double elevation;

    @JsonProperty("daily_units")
    private Map<String, String> dailyUnits;

    private WeeklyData daily;

    @JsonProperty("hourly_units")
    private Map<String, String> hourlyUnits;
    private HourlyData hourly;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getGenerationtimeMs() {
        return generationtimeMs;
    }

    public void setGenerationtimeMs(long generationtimeMs) {
        this.generationtimeMs = generationtimeMs;
    }

    public int getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public void setUtcOffsetSeconds(int utcOffsetSeconds) {
        this.utcOffsetSeconds = utcOffsetSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezoneAbbreviation() {
        return timezoneAbbreviation;
    }

    public void setTimezoneAbbreviation(String timezoneAbbreviation) {
        this.timezoneAbbreviation = timezoneAbbreviation;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public Map<String, String> getDailyUnits() {
        return dailyUnits;
    }

    public void setDailyUnits(Map<String, String> dailyUnits) {
        this.dailyUnits = dailyUnits;
    }

    public WeeklyData getDaily() {
        return daily;
    }

    public void setDaily(WeeklyData daily) {
        this.daily = daily;
    }

    public Map<String, String> getHourlyUnits() {
        return hourlyUnits;
    }

    public void setHourlyUnits(Map<String, String> hourlyUnits) {
        this.hourlyUnits = hourlyUnits;
    }

    public HourlyData getHourly() {
        return hourly;
    }

    public void setHourly(HourlyData hourly) {
        this.hourly = hourly;
    }
}
