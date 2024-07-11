package com.proit.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DailyUnit {
    @JsonProperty("temperature_2m_max")
    private String temperature2m;

    @JsonProperty("rain_sum")
    private String rainSum;

    @JsonProperty("wind_speed_10m_max")
    private String windSpeed10m;
}
