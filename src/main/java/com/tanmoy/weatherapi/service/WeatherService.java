package com.tanmoy.weatherapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tanmoy.weatherapi.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;

    @Value("${openMeteoApi}")
    private String openMeteoBaseApi;

    public WeatherResponseDto getWeatherDetailsByLatLng(double latitude, double longitude) throws JsonProcessingException {
        String concateApi = "/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true&hourly=temperature_2m,relativehumidity_2m,windspeed_10m";
        ResponseEntity<WeatherResponseDto> response = restTemplate.getForEntity(openMeteoBaseApi + concateApi, WeatherResponseDto.class);
        return response.getBody();
    }
}
