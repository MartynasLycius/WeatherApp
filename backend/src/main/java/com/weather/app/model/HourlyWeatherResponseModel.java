package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyWeatherResponseModel {
    private Double latitude;
    private Double longitude;
    private String timezone;
    private String timezone_abbreviation;
    private String elevation;
    private HourlyUnits hourly_units;
    private HourlyWeather hourly;
}
