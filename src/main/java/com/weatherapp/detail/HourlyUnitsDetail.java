package com.weatherapp.detail;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 10 Jun, 2024
 */
@lombok.Data
public class HourlyUnitsDetail {
    private String time;
    private double temperature_2m;
    private double surface_pressure;
    private double wind_speed_10m;
    private double rain;
    public HourlyUnitsDetail(String time, Double temperature_2m, Double surface_pressure, Double wind_speed_10m, Double rain) {
        this.temperature_2m = temperature_2m;
        this.time = time;
        this.surface_pressure = surface_pressure;
        this.wind_speed_10m = wind_speed_10m;
        this.rain = rain;



    }
}
