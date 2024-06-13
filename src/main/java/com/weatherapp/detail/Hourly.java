package com.weatherapp.detail;

import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 10 Jun, 2024
 */
import lombok.Data;
@Data
public class Hourly {
    private List<String> time;
    private List<Double> temperature_2m;
    private List<Double> wind_speed_10m;

    private List<Double> surface_pressure;
    private List<Double> rain;

}
