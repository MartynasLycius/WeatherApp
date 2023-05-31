package com.proit.application.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDataDto {
    private double latitude;

    private double longitude;

    @JsonProperty("generationtime_ms")
    private double generationTimeMs;

    @JsonProperty("utc_offset_seconds")
    private int utcOffsetSeconds;

    private String timezone;

    @JsonProperty("timezone_abbreviation")
    private String timezoneAbbreviation;

    private double elevation;

    @JsonProperty("hourly_units")
    private HourlyUnitsDto hourlyUnits;

    private HourlyDto hourly;

    @JsonProperty("daily_units")
    private DailyUnitsDto dailyUnits;

    private DailyDto daily;
}

