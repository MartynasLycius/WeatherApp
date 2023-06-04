package com.weather.app.service;

import com.weather.app.constants.Endpoints;
import com.weather.app.model.DailyWeatherResponseModel;
import com.weather.app.model.HourlyWeatherResponseModel;
import com.weather.app.utils.WebClientUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WeatherService {
    public DailyWeatherResponseModel getWeatherDaily(String queryString){
        String uri = Endpoints.API_WEATHER + Endpoints.API_DAILY_FORECAST + "?" + queryString;
        WebClient webClient = WebClientUtils.getWeatherClient();
        return webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DailyWeatherResponseModel.class)).block();

    }
    public HourlyWeatherResponseModel getWeatherHourly(String queryString){
        String uri = Endpoints.API_WEATHER + Endpoints.API_HOURLY_FORECAST + "?" + queryString;
        WebClient webClient = WebClientUtils.getWeatherClient();
        return webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(HourlyWeatherResponseModel.class)).block();
    }
}
