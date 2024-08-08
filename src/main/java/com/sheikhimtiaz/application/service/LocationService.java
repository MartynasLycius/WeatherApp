package com.sheikhimtiaz.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheikhimtiaz.application.entity.Location;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.sheikhimtiaz.application.constant.Constants.OPEN_MATEO_BASE_URL_GEOCODING;
import static com.sheikhimtiaz.application.constant.Constants.OPEN_MATEO_VERSION;


@Service
public class LocationService {

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Location> getLocations(String cityName, int page, int size) throws IOException {
        List<Location> allLocations = getLocationsByCityName(cityName, size);
        int start = page * size;
        int end = Math.min((page + 1) * size, allLocations.size());
        return allLocations.subList(start, end);
    }

    private List<Location> getLocationsByCityName(String cityName, int size) throws IOException {
        String apiUrl = String.format(OPEN_MATEO_BASE_URL_GEOCODING
                +OPEN_MATEO_VERSION
                +"/search?format=json&language=en&name=%s&limit=%d", cityName, size);
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JsonNode rootNode = objectMapper.readTree(inline.toString());
            List<Location> locations = new ArrayList<>();
            JsonNode resultsNode = rootNode.path("results");

            if (resultsNode.isArray()) {
                for (JsonNode node : resultsNode) {
                    Location location = new Location();
                    location.setId(node.path("id").asLong());
                    location.setName(node.path("name").asText());
                    location.setLatitude(node.path("latitude").asDouble());
                    location.setLongitude(node.path("longitude").asDouble());
                    location.setElevation(node.path("elevation").asDouble());
                    location.setFeatureCode(node.path("feature_code").asText());
                    location.setCountryCode(node.path("country_code").asText());
                    location.setAdmin1Id(node.path("admin1_id").asLong());
                    location.setAdmin2Id(node.path("admin2_id").asLong());
                    location.setAdmin3Id(node.path("admin3_id").asLong());
                    location.setAdmin4Id(node.path("admin4_id").asLong());
                    location.setTimezone(node.path("timezone").asText());
                    location.setPopulation(node.path("population").asInt());
                    location.setPostcodes(objectMapper.convertValue(node.path("postcodes"), List.class));
                    location.setCountry(node.path("country").asText());
                    location.setAdmin1(node.path("admin1").asText());
                    location.setAdmin2(node.path("admin2").asText());
                    location.setAdmin3(node.path("admin3").asText());
                    location.setAdmin4(node.path("admin4").asText());
                    locations.add(location);
                }
            }
            return locations;

        }
    }
}
