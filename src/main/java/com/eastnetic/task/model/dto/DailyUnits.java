package com.eastnetic.task.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyUnits {

   @JsonProperty("time")
   String time;

   @JsonProperty("temperature_2m_max")
   String temperature2mMax;

   @JsonProperty("temperature_2m_min")
   String temperature2mMin;

   @JsonProperty("sunrise")
   String sunrise;

   @JsonProperty("sunset")
   String sunset;

   @JsonProperty("rain_sum")
   String rainSum;

   @JsonProperty("precipitation_hours")
   String precipitationHours;

   @JsonProperty("precipitation_probability_max")
   String precipitationProbabilityMax;

   @JsonProperty("windspeed_10m_max")
   String windspeed10mMax;

   @JsonProperty("windgusts_10m_max")
   String windgusts10mMax;

   @JsonProperty("weathercode")
   String weathercode;
}