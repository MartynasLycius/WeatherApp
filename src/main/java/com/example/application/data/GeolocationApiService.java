package com.example.application.data;

import com.jsonparsing.Json;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class GeolocationApiService
{

    private List<Location> locationList = new ArrayList<>();

    private final ForecastApiService forecastApiService;

    public GeolocationApiService(ForecastApiService forecastApiService)
    {
        this.forecastApiService = forecastApiService;
    }

    public void fetchDataAndStoreInList(String searchTerm)
    {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://geocoding-api.open-meteo.com/v1/search?name=" + searchTerm;

        String responseBody = restTemplate.getForObject(apiUrl, String.class);

        try
        {
            JsonNode rootNode = Json.parse(responseBody); //parsing json response using my custom Json class
            JsonNode resultsNode = rootNode.get("results");

            if (resultsNode.isArray())
            {
                System.out.println("jsonNode is an array");
                for (JsonNode node : resultsNode)
                {
                    Location location = createEntityFromJsonNode(node);
                    locationList.add(location);
                }
            } else
            {
                System.out.println("jsonNode is not an array");
            }
        } catch (Exception e)
        {
            System.out.println("testing for a bug in geolocationApiService");
            e.printStackTrace(); // i should handle the exception properly, maybe later
        }
        for (Location location : locationList)
        {
            forecastApiService.fetchAndStoreForecasts(location);
        }
    }

    private Location createEntityFromJsonNode(JsonNode node)
    {
        Location location = new Location();
        location.setLocationName(node.get("name").asText());
        location.setLatitude(node.get("latitude").asText());
        location.setLongitude(node.get("longitude").asText());
        if (node.get("country") != null)
        {
            location.setCountry(node.get("country").asText());
        }
        if (node.get("timezone") != null)
        {
            location.setTimezone(node.get("timezone").asText());
        } else
        {
            location.setTimezone("auto");
        }

        return location;
    }

    public List<Location> getLocationList()
    {
        return locationList;
    }

    public void clearLocationList()
    {
        locationList.clear();
    }
}
