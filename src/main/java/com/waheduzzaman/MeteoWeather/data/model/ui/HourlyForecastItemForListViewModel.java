package com.waheduzzaman.MeteoWeather.data.model.ui;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HourlyForecastItemForListViewModel {
    private String time;
    private Double temperature;
    private Double precipitation;
    private Double rainSum;
    private Double wind;
}
