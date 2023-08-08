package com.example.application.http.client;

import com.example.application.dto.DailyForecast;
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
public class HttpDailyForecastRequest {

    private String dailyForecastDataUrlTemplate = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&daily=temperature_2m_max,rain_sum,windspeed_10m_max&timezone=GMT";
    private HttpClient httpClient;
    private HttpRequest httpDailyForecastDataRequest;

//    private Double latitude = 23.7104;
//    private Double longitude = 90.407;

    public DailyForecast getDailyForecast(Double latitude, Double longitude ){
        DailyForecast dailyForecast = new DailyForecast();
        try {
            if(latitude == null || longitude == null){
                return dailyForecast;
            }
            String DailyForecastDataUrl = String.format(dailyForecastDataUrlTemplate, latitude +"", longitude +"");
            httpClient = getHttpClientWithTimeout(3);
            httpClient.connectTimeout().map(Duration::toSeconds)
                    .ifPresent(sec -> System.out.println("Timeout in seconds: " + sec));
            httpDailyForecastDataRequest = HttpRequest.newBuilder()
                    .uri(URI.create(DailyForecastDataUrl))
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
