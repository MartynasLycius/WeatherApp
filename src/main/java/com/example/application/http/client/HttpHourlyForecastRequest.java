package com.example.application.http.client;

import com.example.application.dto.HourlyForecast;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.example.application.http.client.HttpClientTimeout.getHttpClientWithTimeout;

@Component
public class HttpHourlyForecastRequest {

    private String hourlyForecastDataUrlTemplate = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature_2m,rain,windspeed_10m";
    private HttpClient httpClient;
    private HttpRequest httpHourlyForecastDataRequest;

//    private Double latitude = 23.7104;
//    private Double longitude = 90.407;

    public HourlyForecast getHourlyForecast(Double latitude, Double longitude ){
        HourlyForecast hourlyForecast = new HourlyForecast();
        try {
            if(latitude == null || longitude == null){
                return hourlyForecast;
            }
            String hourlyForecastDataUrl = String.format(hourlyForecastDataUrlTemplate, latitude +"", longitude +"");
            httpClient = getHttpClientWithTimeout(3);
            httpClient.connectTimeout().map(Duration::toSeconds)
                    .ifPresent(sec -> System.out.println("Timeout in seconds: " + sec));
            httpHourlyForecastDataRequest = HttpRequest.newBuilder()
                    .uri(URI.create(hourlyForecastDataUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpHourlyForecastDataRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            if (statusCode == 200) {
                String responseBody = response.body();
                // Parse JSON data into an array of Post objects
                ObjectMapper objectMapper = new ObjectMapper();
                hourlyForecast = objectMapper.readValue(responseBody, HourlyForecast.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return hourlyForecast;
    }
}