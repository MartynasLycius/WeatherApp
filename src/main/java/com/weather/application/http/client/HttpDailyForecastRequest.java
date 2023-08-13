package com.weather.application.http.client;

import com.weather.application.data.dto.DailyForecast;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Component
public class HttpDailyForecastRequest {

    private String dailyForecastDataUrlTemplate = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=temperature_2m_max,rain_sum,windspeed_10m_max&timezone=GMT";

    public DailyForecast getDailyForecast(Double latitude, Double longitude ){
        DailyForecast dailyForecast = new DailyForecast();
        try {
            if(latitude == null || longitude == null){
                return dailyForecast;
            }
            String dailyForecastDataUrl = String.format(dailyForecastDataUrlTemplate, latitude +"", longitude +"");
            HttpClient httpClient = HttpClientTimeout.getHttpClientWithTimeout(3);
            httpClient.connectTimeout().map(Duration::toSeconds)
                    .ifPresent(sec -> System.out.println("Timeout in seconds: " + sec));
            HttpRequest httpDailyForecastDataRequest = HttpRequest.newBuilder()
                    .uri(URI.create(dailyForecastDataUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpDailyForecastDataRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            if (statusCode == 200) {
                String responseBody = response.body();
                // Parse JSON data into an array of Post objects
                ObjectMapper objectMapper = new ObjectMapper();
                dailyForecast = objectMapper.readValue(responseBody, DailyForecast.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return dailyForecast;
    }
}
