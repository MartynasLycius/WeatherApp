package com.proit.application.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyFullDataDto {
    private int day;
    private String date;
    private int weatherCode;
    private double temperature2mMax;
    private double temperature2mMin;
    private String temperatureUnit;
    private double rainSum;
    private String rainUnit;
    private double windSpeed10mMax;
    private String windSpeedUnit;
}
