package com.eastnetic.task.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HourlyUnits {

   @JsonProperty("time")
   String time;

   @JsonProperty("temperature_2m")
   String temperature2m;

   @JsonProperty("rain")
   String rain;

   @JsonProperty("windspeed_10m")
   String windspeed10m;

   @JsonProperty("relativehumidity_2m")
   String relativehumidity2m;

   @JsonProperty("precipitation_probability")
   String precipitationProbability;
}