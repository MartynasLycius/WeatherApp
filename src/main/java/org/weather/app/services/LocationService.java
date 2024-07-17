package org.weather.app.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.weather.app.models.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class LocationService extends BaseService<List<Location>>{

    private static final String API_URL_TEMPLATE = "https://geocoding-api.open-meteo.com/v1/search?name=%s";

    public CompletableFuture<List<Location>> fetchLocations(String cityName) {
        String apiUrl = String.format(API_URL_TEMPLATE, cityName);
        return fetch(apiUrl, this::parseResponse);
    }

    private List<Location> parseResponse(String responseBody) {
        List<Location> locations = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            JsonNode results = root.path("results");
            for (JsonNode result : results) {
                Location location = new Location();
                location.setCityName(result.path("name").asText());
                location.setLatitude(result.path("latitude").asDouble());
                location.setLongitude(result.path("longitude").asDouble());
                location.setTimeZone(result.path("country").asText());
                location.setAction("Test action");
                locations.add(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }
}
