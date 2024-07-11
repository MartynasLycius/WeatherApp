package com.proit.weather.service;

import com.proit.weather.api.WeatherAPI;
import com.proit.weather.dto.LocationResult;
import com.proit.weather.dto.WeatherInfo;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class WeatherInfoService {
    private static final String WEATHER_PI_URL = "https://api.open-meteo.com/v1/";
    private static final String LOCATION_API_URL = "https://geocoding-api.open-meteo.com/v1/";
    private static final WeatherAPI weatherAPI = WeatherAPI.connect(WEATHER_PI_URL);

    public static WeatherInfo getWeatherInformation(Double latitude, Double longitude) {
        return weatherAPI.getTodaysWeatherInfo(latitude, longitude);
    }

    public static WeatherInfo getWeatherInformationByDate(Double latitude, Double longitude, LocalDate fromDate, LocalDate toDate) {
        String fromDateStr = fromDate.format(DateTimeFormatter.ISO_DATE);
        String toDateStr = toDate.format(DateTimeFormatter.ISO_DATE);
        return weatherAPI.getWeatherInfoByDate(latitude, longitude, fromDateStr, toDateStr);
    }

    public static Optional<LocationResult> getLocations(String city) {
        if (StringUtils.isEmpty(city)) {
            return Optional.empty();
        }
        final WeatherAPI api = WeatherAPI.connect(LOCATION_API_URL);
        return Optional.of(api.getLocations(city));
    }
}
