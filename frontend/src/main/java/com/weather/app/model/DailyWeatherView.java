package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyWeatherView {
    private String timezone;
    private String time;
    private Double latitude;
    private Double longitude;
    private Double temperature_max;
    private Double temperature_min;
    private Double rain;
    private Double surfaceWind;

}
