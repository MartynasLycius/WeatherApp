package com.eastnetic.task.model.dto;


import com.eastnetic.task.model.dto.Hourly;
import com.eastnetic.task.model.dto.HourlyUnits;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForecastDTO {

   @JsonProperty("latitude")
   double latitude;

   @JsonProperty("longitude")
   double longitude;

   @JsonProperty("generationtime_ms")
   double generationtimeMs;

   @JsonProperty("utc_offset_seconds")
   int utcOffsetSeconds;

   @JsonProperty("timezone")
   String timezone;

   @JsonProperty("timezone_abbreviation")
   String timezoneAbbreviation;

   @JsonProperty("elevation")
   int elevation;

   @JsonProperty("hourly_units")
   HourlyUnits hourlyUnits;

   @JsonProperty("hourly")
   Hourly hourly;

   @JsonProperty("daily_units")
   DailyUnits dailyUnits;

   @JsonProperty("daily")
   Daily daily;

}