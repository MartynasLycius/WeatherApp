package com.weatherapp.detail;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 10 Jun, 2024
 */


import lombok.Data;
@Data
public class WeatherData {
    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    private double elevation;

    private HourlyUnits hourly_units;
    private Hourly hourly;

    private DailyUnits daily_units;
    private Daily daily;
}