package com.example.application.dto;

public class ForecastResponse {
    public double latitude;
    public double longitude;
    public double generationtime_ms;
    public int utc_offset_seconds;
    public String timezone;
    public String timezone_abbreviation;
    public double elevation;
    public CurrentWeather current_weather;
    public HourlyUnits hourly_units;
    public Hourly hourly;
    public DailyUnits daily_units;
    public Daily daily;
}


