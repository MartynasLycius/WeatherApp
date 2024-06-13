package com.weatherapp.detail;

import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 10 Jun, 2024
 */
import lombok.Data;
@Data
public class Daily {
    private List<String> time;
    private List<Integer> weather_code;
    private List<Double> wind_speed_10m_max;

private List<Double> temperature_2m_max;
private List<Double> rain_sum;



}
