package com.proit.application.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DailyDto implements Serializable {
    private List<String> time;

    @JsonProperty("weathercode")
    private List<Integer> weatherCode;

    @JsonProperty("temperature_2m_max")
    private List<Double> temperature2mMax;

    @JsonProperty("temperature_2m_min")
    private List<Double> temperature2mMin;

    private List<String> sunrise;

    private List<String> sunset;

    @JsonProperty("precipitation_probability_max")
    private List<Integer> precipitationProbabilityMax;

    @JsonProperty("windspeed_10m_max")
    private List<Double> windspeed10mMax;

    @JsonProperty("rain_sum")
    private List<Double> rainSum;
}
