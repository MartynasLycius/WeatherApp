package com.eastnetic.task.model.dto;
import java.util.List;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Daily {

   @JsonProperty("time")
   List<Date> time;

   @JsonProperty("temperature_2m_max")
   List<Double> temperature2mMax;

   @JsonProperty("temperature_2m_min")
   List<Double> temperature2mMin;

   @JsonProperty("sunrise")
   List<Date> sunrise;

   @JsonProperty("sunset")
   List<Date> sunset;

   @JsonProperty("rain_sum")
   List<Integer> rainSum;

   @JsonProperty("precipitation_hours")
   List<Integer> precipitationHours;

   @JsonProperty("precipitation_probability_max")
   List<Integer> precipitationProbabilityMax;

   @JsonProperty("windspeed_10m_max")
   List<Double> windspeed10mMax;

   @JsonProperty("windgusts_10m_max")
   List<Double> windgusts10mMax;
}