package org.vaadin.example.service;

import com.fasterxml.jackson.core.json.JsonReadContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vaadin.example.entity.Location;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LocationService {
    private static final String GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/v1/";

    public List<Location> getLocations (String locationName) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(GEOCODING_BASE_URL + "search?name=" + locationName + "&count=100");

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        List<Location> locations = new ArrayList<>();
        if (response.getStatus() == 200) {
            String jsonResponse = response.readEntity(String.class);
            JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(jsonResponse));
            JsonObject jsonObject = jsonReader.readObject();
            JsonArray results = jsonObject.getJsonArray("results");

            for (JsonObject result : results.getValuesAs(JsonObject.class)) {
                Location location = new Location(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getJsonNumber("latitude").doubleValue(),
                        result.getJsonNumber("longitude").doubleValue(),
                        result.getString("country")
                );
                locations.add(location);
            }
        }
        response.close();
        client.close();

        return locations;
    }

    public Location getLocationById (Integer locationId) {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(GEOCODING_BASE_URL + "get?id=" + locationId);
        Response response = target.request(MediaType.APPLICATION_JSON).get();

        Location location;

        if (response.getStatus() == 200) {
            String jsonResponse = response.readEntity(String.class);
            JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(jsonResponse));
            JsonObject jsonObject = jsonReader.readObject();

            Integer id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            Double latitude = jsonObject.getJsonNumber("latitude").doubleValue();
            Double longitude = jsonObject.getJsonNumber("longitude").doubleValue();
            String country = jsonObject.getString("country");

            location =  new Location(id, name, latitude, longitude, country);

        } else {
            location = null;
        }
        response.close();
        client.close();

        return location;
    }
}
