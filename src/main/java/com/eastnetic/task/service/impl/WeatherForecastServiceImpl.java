package com.eastnetic.task.service.impl;

import com.eastnetic.task.model.dto.ForecastDTO;
import com.eastnetic.task.service.ForecastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;

@Service
@Slf4j
public class WeatherForecastServiceImpl implements ForecastService {

    @Autowired
    WebClient webClient;
    @Value("${weather.forecast.api.url}")
    private String forecastUrl;

    /**
     * Get weather forecast data from service call
     * @param latitude,longitude,timezone
     * @return ForecastDTO
     * @throws
     */
    @Override
    public ForecastDTO getWeatherForecasts(double latitude, double longitude, String timezone) {
        ForecastDTO forecastDTO;
        String url = MessageFormat.format(this.forecastUrl, latitude, longitude, timezone);
        try{
            forecastDTO = this.webClient
                    .get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ForecastDTO.class)
                    .block();
            log.debug(forecastDTO.toString());
        } catch (Exception e) {
            forecastDTO = new ForecastDTO();
        }
        return forecastDTO;
    }
}
