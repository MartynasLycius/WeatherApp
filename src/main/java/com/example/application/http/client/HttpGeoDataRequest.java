package com.example.application.http.client;

import com.example.application.dto.GeoCode;
import com.example.application.dto.GeoCodeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static com.example.application.http.client.HttpClientTimeout.getHttpClientWithTimeout;

@Component
public class HttpGeoDataRequest {

    private String geoDataUrlTemplate = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=%s&language=en&format=json";
    private HttpClient httpClient;
    private HttpRequest httpGetGeoDataRequest;

    private int geoCodeSize = 100;



    public GeoCodeResult getGeoCodeResult(String cityName){
        GeoCodeResult results = new GeoCodeResult();
        try {
            if(cityName == null || Strings.isBlank(cityName)){
                return results;
            }
            String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8.toString());
            String geoDataUrl = String.format(geoDataUrlTemplate, encodedCityName, geoCodeSize+"");
            httpClient = getHttpClientWithTimeout(3);
            httpClient.connectTimeout().map(Duration::toSeconds)
                    .ifPresent(sec -> System.out.println("Timeout in seconds: " + sec));
            httpGetGeoDataRequest = HttpRequest.newBuilder()
                    .uri(URI.create(geoDataUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(httpGetGeoDataRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            if (statusCode == 200) {
                String responseBody = response.body();
                // Parse JSON data into an array of Post objects
                ObjectMapper objectMapper = new ObjectMapper();
                results = objectMapper.readValue(responseBody, GeoCodeResult.class);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return results;
    }
}
