package com.example.application.services;
import com.example.application.services.ForecastAPI;
import com.example.application.services.GeocodingAPI;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class API {
      @Bean
      public ForecastAPI forecast() {
        return new ForecastAPI();
      }

      @Bean
      public GeocodingAPI geocoding() {
        return new GeocodingAPI();
      }
}
