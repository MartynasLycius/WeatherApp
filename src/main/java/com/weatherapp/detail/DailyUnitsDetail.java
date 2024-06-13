package com.weatherapp.detail;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 10 Jun, 2024
 */
@lombok.Data
public class DailyUnitsDetail {
    private double latitude;
    private double longitude;
    private  String time;
    private  String weather_code;
    private String wind_speed_10m_max;

    private String temperature_2m_max;
    private String rain_sum;


    public  DailyUnitsDetail() {
    }
    public DailyUnitsDetail(String time, Integer weather_code, Double wind_speed_10m_max, double latitude, double longitude,double temperature_2m_max, double rain_sum) {

        this.time = time;
        this.weather_code = String.valueOf(weather_code);
        this.wind_speed_10m_max = String.valueOf(wind_speed_10m_max);
        this.latitude = latitude;
        this.longitude = longitude;
        this.temperature_2m_max = String.valueOf(temperature_2m_max);
        this.rain_sum = String.valueOf(rain_sum);
    }
}
