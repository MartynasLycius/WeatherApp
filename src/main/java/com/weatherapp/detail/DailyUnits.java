package com.weatherapp.detail;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 10 Jun, 2024
 */

import lombok.Data;
@Data
public class DailyUnits {
    private String time;
    private String weather_code;
    private String temperature_2m_max;
    private String rain_sum;
    private String wind_speed_10m_max;
}
