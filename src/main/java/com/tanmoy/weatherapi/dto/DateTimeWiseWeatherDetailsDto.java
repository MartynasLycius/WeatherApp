package com.tanmoy.weatherapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeWiseWeatherDetailsDto {

    private Date time;
    private Double temperature_2m;
    private Integer relativehumidity_2m;
    private Double windspeed_10m;


    public static List<DateTimeWiseWeatherDetailsDto> getDateTimeWiseWeatherDetailsList(WeatherDetailsHourlyDto hourlyDto) {
        List<DateTimeWiseWeatherDetailsDto> list = new ArrayList<>();
        for (int i = 0; i < hourlyDto.getTime().size(); i++) {
            list.add(from(hourlyDto.getTime().get(i), hourlyDto.getTemperature_2m().get(i), hourlyDto.getRelativehumidity_2m().get(i), hourlyDto.getWindspeed_10m().get(i)));
        }
        return list;
    }

    public static DateTimeWiseWeatherDetailsDto from(Date time, Double temperature_2m, Integer relativehumidity_2m, Double windspeed_10m) {
        return new DateTimeWiseWeatherDetailsDto(time, temperature_2m, relativehumidity_2m, windspeed_10m);
    }

}
