package com.example.application.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.application.data.DailyForecast;
import com.example.application.data.HourlyForecast;

import reactor.core.publisher.Mono;

public class ForecastAPI {
    private WebClient webClient;

    public ForecastAPI() {
        this.webClient = WebClient.builder()
          .baseUrl("https://api.open-meteo.com/v1/forecast")
          .build();
    }
    public DailyForecast getForecastDaily(Double latitude, Double longitude) {
		Mono<DailyForecast> response = this.webClient.get().uri("?latitude={latitude}&longitude={longitude}&timezone=EET&daily=temperature_2m_max", latitude, longitude)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<DailyForecast>() {});
        DailyForecast readers = response.block();
        return readers;
	  }
    public HourlyForecast getForecastHoursTemp(Double latitude, Double longitude) {
		Mono<HourlyForecast> response = this.webClient.get().uri("?latitude={latitude}&longitude={longitude}&timezone=EET&hourly=temperature_2m,rain,wind_speed_10m&forecast_days=1", latitude, longitude)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<HourlyForecast>() {});
        HourlyForecast readers = response.block();
        return readers;
	  }
}
