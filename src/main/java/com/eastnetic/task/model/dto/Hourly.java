package com.eastnetic.task.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hourly {

   @JsonProperty("time")
   List<String> time;

   @JsonProperty("temperature_2m")
   List<Double> temperature2m;

   @JsonProperty("rain")
   List<Integer> rain;

   @JsonProperty("windspeed_10m")
   List<Integer> windspeed10m;

   @JsonProperty("relativehumidity_2m")
   List<Integer> relativehumidity2m;

   @JsonProperty("precipitation_probability")
   List<Integer> precipitationProbability;

   @JsonProperty("weathercode")
   List<Integer> weathercode;
}