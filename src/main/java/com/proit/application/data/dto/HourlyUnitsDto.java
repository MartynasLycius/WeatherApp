package com.proit.application.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class HourlyUnitsDto implements Serializable {
    private String time;

    @JsonProperty("temperature_2m")
    private String temperature2m;

    @JsonProperty("apparent_temperature")
    private String apparentTemperature;

    @JsonProperty("windspeed_10m")
    private String windSpeed10m;

    @JsonProperty("weathercode")
    private String weatherCode;

    @JsonProperty("precipitation_probability")
    private String precipitationProbability;
}

