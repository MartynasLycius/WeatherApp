package com.weather.application.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HourlyForecast {

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("generationtime_ms")
    private Double generationtimeMs;

    @JsonProperty("utc_offset_seconds")
    private Long utcOffsetSeconds;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;

    @JsonProperty("elevation")
    private Double elevation;

    @JsonProperty("hourly_units")
    private HourlyUnit hourlyUnits;

    @JsonProperty("hourly")
    private HourlyInfo hourly;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getGenerationtimeMs() {
        return generationtimeMs;
    }

    public void setGenerationtimeMs(Double generationtimeMs) {
        this.generationtimeMs = generationtimeMs;
    }

    public Long getUtcOffsetSeconds() {
        return utcOffsetSeconds;
    }

    public void setUtcOffsetSeconds(Long utcOffsetSeconds) {
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

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public HourlyUnit getHourlyUnits() {
        return hourlyUnits;
    }

    public void setHourlyUnits(HourlyUnit hourlyUnits) {
        this.hourlyUnits = hourlyUnits;
    }

    public HourlyInfo getHourly() {
        return hourly;
    }

    public void setHourly(HourlyInfo hourly) {
        this.hourly = hourly;
    }

    @Override
    public String toString() {
        return "HourlyForecast{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", generationtimeMs=" + generationtimeMs +
                ", utcOffsetSeconds=" + utcOffsetSeconds +
                ", timezone='" + timezone + '\'' +
                ", timezoneAbbreviation='" + timezoneAbbreviation + '\'' +
                ", elevation=" + elevation +
                ", hourlyUnits=" + hourlyUnits +
                ", hourly=" + hourly +
                '}';
    }
}
