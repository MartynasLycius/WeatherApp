package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourlyUnits {
    private String time;
    private String temperature_2m;
    private String rain;
    private String surface_pressure;
    private String windspeed_10m;
    private String winddirection_10m;
}
