package com.weather.application.http.client;

import com.weather.application.data.dto.DailyForecast;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class HttpDailyForecastRequest {

    private static final Logger LOGGER = Logger.getLogger(HttpDailyForecastRequest.class.getName());
    @Value("${weather.daily.forecast.url.template}")
    private String dailyForecastDataUrlTemplate;

    public DailyForecast getDailyForecast(Double latitude, Double longitude ){
        LOGGER.log(Level.INFO, "Get DailyForecast request start with latitude {0} and longitude {1}", new Object[]{latitude, longitude});
        DailyForecast dailyForecast = new DailyForecast();
        try {
            if(latitude == null || longitude == null){
                return dailyForecast;
            }
            String dailyForecastDataUrl = String.format(dailyForecastDataUrlTemplate, latitude +"", longitude +"");
            HttpClient httpClient = HttpClientTimeout.getHttpClientWithTimeout(3);
            httpClient.connectTimeout().map(Duration::toSeconds)
                    .ifPresent(sec -> LOGGER.info("Timeout in seconds: " + sec));
            HttpRequest httpDailyForecastDataRequest = HttpRequest.newBuilder()
                    .uri(URI.create(dailyForecastDataUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpDailyForecastDataRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            LOGGER.log(Level.INFO, "Status Code: {0}", statusCode);

            if (statusCode == 200) {
                String responseBody = response.body();
                // Parse JSON data into an array of Post objects
                ObjectMapper objectMapper = new ObjectMapper();
                dailyForecast = objectMapper.readValue(responseBody, DailyForecast.class);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while get dailyForecast with message {0}", e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while get dailyForecast with message {0}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        LOGGER.log(Level.INFO, "Get DailyForecast request end with dailyForecast {0}", new Object[]{dailyForecast});
        return dailyForecast;
    }
}
