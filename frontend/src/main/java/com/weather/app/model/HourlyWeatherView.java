package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyWeatherView {
    private String times;
    private Double surfaceWind;
    private Double temperature;
    private Double rain;
    private Double timeZone;
}
