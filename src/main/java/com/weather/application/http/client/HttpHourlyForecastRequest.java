package com.weather.application.http.client;

import com.weather.application.data.dto.HourlyForecast;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.weather.application.http.client.HttpClientTimeout.getHttpClientWithTimeout;

@Component
public class HttpHourlyForecastRequest {

    private static final Logger LOGGER = Logger.getLogger(HttpHourlyForecastRequest.class.getName());

    private String hourlyForecastDataUrlTemplate = "https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&hourly=temperature_2m,rain,windspeed_10m&start_date=%s&end_date=%s";

    public HourlyForecast getHourlyForecast(Double latitude, Double longitude, String startDate, String endDate ){
        LOGGER.log(Level.INFO, "Get HourlyForecast request start with latitude {0}, longitude {1}, startDate {2} " +
                "and endDate {3}", new Object[]{latitude, longitude, startDate, endDate});
        HourlyForecast hourlyForecast = new HourlyForecast();
        try {
            if(latitude == null || longitude == null){
                return hourlyForecast;
            }
            String hourlyForecastDataUrl = String.format(hourlyForecastDataUrlTemplate, latitude +"", longitude +"", startDate, endDate);
            HttpClient httpClient = getHttpClientWithTimeout(3);
            httpClient.connectTimeout().map(Duration::toSeconds)
                    .ifPresent(sec -> LOGGER.info("Timeout in seconds: " + sec));
            HttpRequest httpHourlyForecastDataRequest = HttpRequest.newBuilder()
                    .uri(URI.create(hourlyForecastDataUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpHourlyForecastDataRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            LOGGER.log(Level.INFO, "Status Code: {0}", statusCode);

            if (statusCode == 200) {
                String responseBody = response.body();
                // Parse JSON data into an array of Post objects
                ObjectMapper objectMapper = new ObjectMapper();
                hourlyForecast = objectMapper.readValue(responseBody, HourlyForecast.class);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while get hourlyForecast with message {0}", e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while get hourlyForecast with message {0}", e.getMessage());
            throw new RuntimeException(e);
        }
        LOGGER.log(Level.INFO, "Get hourlyForecast request end with dailyForecast {0}", new Object[]{hourlyForecast});
        return hourlyForecast;
    }
}
