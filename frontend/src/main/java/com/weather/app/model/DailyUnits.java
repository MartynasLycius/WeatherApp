package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyUnits {
    private String time;
    private String temperature_2m_max;
    private String temperature_2m_min;
    private String rain_sum;
    private String windspeed_10m_max;
    private String windgusts_10m_max;
    private String surface_pressure;
    private String rain;
    private String windspeed_10m;
    private String winddirection_10m;
}
