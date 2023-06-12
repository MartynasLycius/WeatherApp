package com.example.application.service.impl;

import com.example.application.data.dto.LocationDto;
import com.example.application.data.dto.RestTemplateDTO;
import com.example.application.data.dto.meteo.GeoCodeResponse;
import com.example.application.data.dto.meteo.WeatherForecastResponse;
import com.example.application.service.OpenMeteoService;
import com.example.application.service.RestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OpenMeteoServiceImpl implements OpenMeteoService {
    @Value("${open.meteo.api.location-search}")
    private String locationSearchApiLink;

    @Value("${open.meteo.api.daily-forcast}")
    private String dailyWeatherForecastApiLink;

    private final RestTemplateService restTemplateService;


    @Override
    public List<LocationDto> getLocations(String query) {
        log.info("Fetching locations for query: {}", query);

        String requestUrl = MessageFormat.format(locationSearchApiLink, query);

        RestTemplateDTO<GeoCodeResponse> restTemplateDTO = new RestTemplateDTO<>(GeoCodeResponse.class);

        restTemplateDTO.setUrl(requestUrl).setHttpMethod(HttpMethod.GET);

        GeoCodeResponse geoCodeResponse = restTemplateService.execute(restTemplateDTO);


        log.info("Successfully fetched locations for query: {}", query);

        return mapToLocationDto(geoCodeResponse);

    }

    @Override
    public WeatherForecastResponse getWeatherData(double lat, double lang, String timezone) {
        log.info("Fetching weather data for latitude: {}, longitude: {}, timezone: {}", lat, lang, timezone);

        String requestUrl = MessageFormat.format(dailyWeatherForecastApiLink, lat, lang, timezone);

        RestTemplateDTO<WeatherForecastResponse> restTemplateDTO = new RestTemplateDTO<>(WeatherForecastResponse.class);

        restTemplateDTO.setUrl(requestUrl).setHttpMethod(HttpMethod.GET);

        return restTemplateService.execute(restTemplateDTO);
    }

    private List<LocationDto> mapToLocationDto(GeoCodeResponse geoCodeResponse) {


        var locations = new ArrayList<LocationDto>();

        geoCodeResponse.getResults().forEach(result -> {
            var locationDto = LocationDto.builder()
                    .id(result.getId())
                    .name(result.getName())
                    .latitude(result.getLatitude())
                    .longitude(result.getLongitude())
                    .address(getAddress(result))
                    .country(result.getCountry())
                    .countryCode(result.getCountryCode())
                    .build();
            locations.add(locationDto);
        });

        return locations;
    }

    private String getAddress(GeoCodeResponse.MeteoLocation result) {

        if (result.getAdmin1() != null && result.getAdmin2() != null) {
            return String.format("%s, %s", result.getAdmin2(), result.getAdmin1());
        } else if (result.getAdmin2() != null) {
            return result.getAdmin2();
        } else {
            return result.getAdmin1();
        }

    }

}
