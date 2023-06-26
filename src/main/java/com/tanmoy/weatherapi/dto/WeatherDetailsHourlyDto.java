package com.tanmoy.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDetailsHourlyDto {

    private List<Date> time;
    private List<Double> temperature_2m;
    private List<Integer> relativehumidity_2m;
    private List<Double> windspeed_10m;
}
