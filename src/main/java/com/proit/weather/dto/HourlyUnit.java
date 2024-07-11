package com.proit.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class HourlyUnit {
    @JsonProperty("temperature_2m")
    private String temperature2m;

    @JsonProperty("wind_speed_10m")
    private String windSpeed10m;

    @JsonProperty("rain")
    private String rain;
}
