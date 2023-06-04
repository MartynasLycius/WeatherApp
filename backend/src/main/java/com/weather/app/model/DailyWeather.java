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
public class DailyWeather {
    private List<String> time;
    private List<Double> temperature_2m_max;
    private List<Double> temperature_2m_min;
    private List<Double> rain_sum;
    private List<Double> windspeed_10m_max;
    private List<Double> windgusts_10m_max;
}
