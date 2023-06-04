package com.weather.app.utils;

import com.weather.app.constants.AppConstants;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientUtils {
    public static WebClient getWeatherClient(){
        return getWebClient(AppConstants.WEATHER_APP_BASE_URL);
    }
    private static WebClient getWebClient(String baseUrl){
        return WebClient.create(baseUrl);
    }
}
