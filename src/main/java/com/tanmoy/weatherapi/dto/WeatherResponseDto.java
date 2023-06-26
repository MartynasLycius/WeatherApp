package com.tanmoy.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseDto {

    private double latitude;
    private double longitude;
    private String timezone;
    private CurrentWeatherDto current_weather;
    private Units hourly_units;
    private WeatherDetailsHourlyDto hourly;

}
