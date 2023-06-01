package com.proit.application.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HourlyDto implements Serializable {
    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperature2m;

    @JsonProperty("apparent_temperature")
    private List<Double> apparentTemperature;

    private List<Double> rain;

    @JsonProperty("windspeed_10m")
    private List<Double> windSpeed10m;

    @JsonProperty("weathercode")
    private List<Integer> weatherCode;

    @JsonProperty("precipitation_probability")
    private List<Integer> precipitationProbability;
}
