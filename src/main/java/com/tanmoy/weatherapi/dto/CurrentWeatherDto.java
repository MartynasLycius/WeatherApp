package com.tanmoy.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherDto {

    private float temperature;
    private float windspeed;
    private float winddirection;
    private float weathercode;

}
