package com.weather.application.http.client;

import com.vaadin.flow.server.VaadinSession;
import com.weather.application.data.dto.GeoCodeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.application.exception.CustomErrorHandler;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.weather.application.http.client.HttpClientTimeout.getHttpClientWithTimeout;

@Component
public class HttpGeoDataRequest {

    private static final Logger LOGGER = Logger.getLogger(HttpGeoDataRequest.class.getName());
    @Value("${weather.geo.code.url.template}")
    private String geoDataUrlTemplate;
    private int geoCodeSize = 100;

    public GeoCodeResult getGeoCodeResult(String cityName){
        LOGGER.log(Level.INFO, "Get geo code request start with city name {0}", new Object[]{cityName});
        GeoCodeResult results = new GeoCodeResult();
        try {
            if(cityName == null || Strings.isBlank(cityName)){
                return results;
            }
            String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
            String geoDataUrl = String.format(geoDataUrlTemplate, encodedCityName, geoCodeSize+"");
            HttpClient httpClient = getHttpClientWithTimeout(3);
            httpClient.connectTimeout().map(Duration::toSeconds)
                    .ifPresent(sec -> LOGGER.info("Timeout in seconds: " + sec));
            HttpRequest httpGetGeoDataRequest = HttpRequest.newBuilder()
                    .uri(URI.create(geoDataUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpGetGeoDataRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            LOGGER.log(Level.INFO, "Status Code: {0}", statusCode);

            if (statusCode == 200) {
                String responseBody = response.body();
                // Parse JSON data into an array of Post objects
                ObjectMapper objectMapper = new ObjectMapper();
                results = objectMapper.readValue(responseBody, GeoCodeResult.class);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while get geo code with message {0}", e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error occurred while get geo code with message {0}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        LOGGER.log(Level.INFO, "Get geo code request end with GeoCodeResult {0}", new Object[]{results});
        return results;
    }
}
