package com.waheduzzaman.MeteoWeather.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Data
public class AppConfig {

    @Value("${application.name}")
    private String APP_NAME;

    @Value("${meteo.location.api.base}")
    private String LOCATION_API;

    @Value("${meteo.location.api.search.path}")
    private String LOCATION_SEARCH_PATH;

    @Value("${meteo.weather.api.base}")
    private String WEATHER_API;

    @Value("${meteo.weather.api.forecase.path}")
    private String WEATHER_FORECAST_PATH;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}

