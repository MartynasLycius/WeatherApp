package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyWeather {
    private String timezone;
    private List<String> time;
    private List<Double> temperature_2m;
    private List<Double> rain;
    private List<Double> surface_pressure;
    private List<Double> windspeed_10m;
    private List<Double> winddirection_10m;
}
