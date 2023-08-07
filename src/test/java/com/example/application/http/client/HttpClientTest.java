package com.example.application.http.client;

import com.example.application.dto.GeoCodeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.example.application.http.client.HttpClientTimeout.getHttpClientWithTimeout;

public class HttpClientTest {

    private String geoDataUrl = "https://geocoding-api.open-meteo.com/v1/search?name=%s&count=%s&language=en&format=json";

    private HttpClient httpClient;
    private HttpRequest httpGetGeoDataRequest;

    private int geoCodeSize = 10;

    @BeforeEach
    public void setUp(){
        geoDataUrl = String.format(geoDataUrl, "Dhaka", 10+"");
        httpClient = getHttpClientWithTimeout(3);
        httpClient.connectTimeout().map(Duration::toSeconds)
                .ifPresent(sec -> System.out.println("Timeout in seconds: " + sec));

        httpGetGeoDataRequest = HttpRequest.newBuilder()
                        .uri(URI.create(geoDataUrl))
                        .GET()
                        .build();
    }

    @Test
    public void getGeoData(){
        try {
            HttpResponse<String> response = httpClient.send(httpGetGeoDataRequest, BodyHandlers.ofString());
            int statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            if (statusCode == 200) {
                String responseBody = response.body();

                // Parse JSON data into an array of Post objects
                ObjectMapper objectMapper = new ObjectMapper();
                GeoCodeResult results = objectMapper.readValue(responseBody, GeoCodeResult.class);

                // Process and use the Post DTO objects as needed
//                for (GeoCode geoCode : results.getResults()) {
//                    System.out.println("Post getName: " + geoCode.getName());
//                    System.out.println("Post getCountry: " + geoCode.getCountry());
//                    System.out.println("Post getLongitude: " + geoCode.getLongitude());
//                    System.out.println("Post getLatitude: " + geoCode.getLatitude());
//                    System.out.println("Post getId: " + geoCode.getId());
//                    System.out.println("---------------------------------------");
//                }

                assertTrue(geoCodeSize == results.getResults().length);


            } else {
                System.out.println("Request failed with status code: " + statusCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
