package com.proit.application.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DailyUnitsDto implements Serializable {
    private String time;

    @JsonProperty("weathercode")
    private String weatherCode;

    @JsonProperty("temperature_2m_max")
    private String temperature2mMax;

    @JsonProperty("temperature_2m_min")
    private String temperature2mMin;

    private String sunrise;
    private String sunset;

    @JsonProperty("precipitation_probability_max")
    private String precipitationProbabilityMax;

    @JsonProperty("windspeed_10m_max")
    private String windSpeed10mMax;

    @JsonProperty("rain_sum")
    private String rainSum;
}
