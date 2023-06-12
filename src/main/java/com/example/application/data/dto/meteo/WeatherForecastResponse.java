package com.example.application.data.dto.meteo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeatherForecastResponse {
    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;

    private String timezone;
    private String timezone_abbreviation;
    private double elevation;
    private HourlyUnits hourly_units;
    private Hourly hourly;
    private DailyUnits daily_units;
    private Daily daily;
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Hourly {
        private List<String> time;
        private List<Double> temperature_2m;
        private List<Double> apparent_temperature;
        private List<Double> windspeed_10m;
        private List<Integer> weathercode;
        private List<Long> precipitation_probability;

        private List<Double> rain;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class HourlyUnits {
        private String time;
        private String temperature_2m;
        private String apparent_temperature;
        private String windspeed_10m;
        private String weathercode;
        private String precipitation_probability;
        private String rain;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Daily {
        private List<String> time;
        private List<Integer> weathercode;
        private List<Double> temperature_2m_max;
        private List<Double> temperature_2m_min;
        private List<String> sunrise;
        private List<String> sunset;
        private List<Long> precipitation_probability_max;
        private List<Double> windspeed_10m_max;
        private List<Double> rain_sum;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class DailyUnits {
        private String time;
        private String weathercode;
        private String temperature_2m_max;
        private String temperature_2m_min;
        private String sunrise;
        private String sunset;
        private String precipitation_probability_max;
        private String windspeed_10m_max;
        private String rain_sum;
    }
}