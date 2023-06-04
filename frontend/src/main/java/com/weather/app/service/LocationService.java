package com.weather.app.service;

import com.weather.app.constants.Endpoints;
import com.weather.app.model.LocationResponseModel;
import com.weather.app.utils.WebClientUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LocationService {
    public LocationResponseModel getLocation(String city){
        if (city==null) return new LocationResponseModel();
        WebClient webClient = WebClientUtils.getWeatherClient();
        return webClient.get()
                .uri(Endpoints.API_LOCATION+"/"+city)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(LocationResponseModel.class))
                .block();
    }

}
