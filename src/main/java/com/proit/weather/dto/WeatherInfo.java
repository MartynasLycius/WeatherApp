package com.proit.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherInfo {
    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("generationtime_ms")
    private Double generationTime;

    @JsonProperty("utc_offset_seconds")
    private Integer utcOffsetSeconds;

    @JsonProperty("timezone")
    private String timeZone;

    @JsonProperty("timezone_abbreviation")
    private String timeZoneAbbreviation;

    @JsonProperty("elevation")
    private Integer elevation;

    @JsonProperty("daily_units")
    private DailyUnit dailyUnits;

    @JsonProperty("hourly_units")
    private HourlyUnit hourlyUnits;

    @JsonProperty("daily")
    private DailyUnitDetails dailyUnitDetails;

    @JsonProperty("hourly")
    private HourlyUnitDetails hourlyUnitDetails;

    @JsonIgnore
    public List<DailyUnitDetails.DailyDetails> getDailyForecast() {
        return this.dailyUnitDetails.getDailyDetails();
    }

    @JsonIgnore
    public List<HourlyUnitDetails.HourlyDetails> getHourlyForecast(DailyUnitDetails.DailyDetails dailyDetails) {
        return this.hourlyUnitDetails.getHourlyDetails(dailyDetails);
    }
}
