package com.proit.application.restclient;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.dto.WeatherDataDto;

import java.util.List;

public interface WeatherRestClient {
    List<LocationDto> getLocations(String query);
    WeatherDataDto getWeatherData(double lat, double lang, String timezone);
}
