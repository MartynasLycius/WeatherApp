package org.vaadin.example.api;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.vaadin.example.util.Tools;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class WeatherApiClient {
    private static final String LOCATION_API_URL = "https://geocoding-api.open-meteo.com/v1/";
    private static final String WEATHER_API_URL = "https://api.open-meteo.com/v1/";

    public List<JsonObject> getLocationsByCityName(String cityName, int limit) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(LOCATION_API_URL + "search")
                .queryParam("name", cityName)
                .queryParam("count", limit);

        Response response = target.request().get();
        String jsonResponse = response.readEntity(String.class);
        response.close();

        JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse));
        JsonObject jsonObject = jsonReader.readObject();
        JsonArray locationsArray = jsonObject.getJsonArray("results");

        List<JsonObject> locations = new ArrayList<>();

        // Check if locationsArray is null before attempting to access its size
        if (locationsArray != null) {
            for (int i = 0; i < locationsArray.size(); i++) {
                locations.add(locationsArray.getJsonObject(i));
            }
        } else {
            // Handle the case when locationsArray is null (e.g., log an error, display a message)
            System.err.println("No 'results' array found in the JSON response.");
        }


        return locations;
    }

    public JsonObject getWeatherForecastDaily(double latitude, double longitude) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(WEATHER_API_URL + "forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("forecast_days", Tools.WEATHER_FORECAST_DAYS_SEVEN)
                .queryParam(Tools.WEATHER_FORECAST_TYPE_DAILY, Tools.WEATHER_FORECAST_TYPE_DAILY_PARAMS);

        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String jsonResponse = response.readEntity(String.class);
            JsonObject responseObject = Json.createReader(new StringReader(jsonResponse)).readObject();
            response.close();
            return responseObject;
        } else {
            System.err.println("Failed to fetch daily forecast: " + response.getStatusInfo().getReasonPhrase());
            response.close();
            return null;
        }
    }
    public JsonObject getWeatherForecastHourly(double latitude, double longitude, String date) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(WEATHER_API_URL + "forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("start_date", date)
                .queryParam("end_date", date)
                .queryParam(Tools.WEATHER_FORECAST_TYPE_HOURLY, Tools.WEATHER_FORECAST_TYPE_HOURLY_PARAMS);

        Response response = target.request().get();
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String jsonResponse = response.readEntity(String.class);
            JsonObject responseObject = Json.createReader(new StringReader(jsonResponse)).readObject();
            response.close();
            return responseObject;
        } else {
            System.err.println("Failed to fetch daily forecast: " + response.getStatusInfo().getReasonPhrase());
            response.close();
            return null;
        }
    }

}

