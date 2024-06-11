package org.vaadin.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.vaadin.example.entity.DailyAndHourlyForecast;
import org.vaadin.example.entity.ForeCast;
import org.vaadin.example.entity.ForeCastUnit;
import org.vaadin.example.entity.Location;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class ForecastService {
    private static final String FORECAST_BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private static final Logger LOGGER = Logger.getLogger(ForecastService.class.getName());
    @Inject
    LocationService locationService;
    public ForeCast getDailyForCast(Integer locationId, Integer forecastLength) {

        Location location = locationService.getLocationById(locationId);
        String timezone = "";
        ForeCastUnit foreCastUnit = null;
        List<DailyAndHourlyForecast> dailyAndHourlyForecasts = new ArrayList<>();

        if (location != null) {
            Client client = ClientBuilder.newClient();
            String URL = FORECAST_BASE_URL + "?latitude=" + location.getLatitude()
                    + "&longitude=" + location.getLongitude()
                    + "&daily=temperature_2m_max,temperature_2m_min,rain_sum,wind_speed_10m_max&forecast_days=" + forecastLength;
            LOGGER.info(URL);

            WebTarget target = client.target(URL);

            Response response = target.request(MediaType.APPLICATION_JSON).get();


            if (response.getStatus() == 200) {
                String jsonResponse = response.readEntity(String.class);
                JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(jsonResponse));
                JsonObject jsonObject = jsonReader.readObject();

                timezone = jsonObject.getString("timezone");

                JsonObject daily_units = jsonObject.getJsonObject("daily_units");
                foreCastUnit = new ForeCastUnit(
                        daily_units.getString("time"),
                        daily_units.getString("temperature_2m_max"),
                        daily_units.getString("temperature_2m_min"),
                        daily_units.getString("rain_sum"),
                        daily_units.getString("wind_speed_10m_max")
                );

                JsonObject daily = jsonObject.getJsonObject("daily");
                JsonArray time = daily.getJsonArray("time");
                JsonArray temperature_2m_max = daily.getJsonArray("temperature_2m_max");
                JsonArray temperature_2m_min = daily.getJsonArray("temperature_2m_min");
                JsonArray rain_sum = daily.getJsonArray("rain_sum");
                JsonArray wind_speed_10m_max = daily.getJsonArray("wind_speed_10m_max");

                for (int i=0; i < time.size(); i++) {
                    DailyAndHourlyForecast dailyAndHourlyForecast = new DailyAndHourlyForecast(
                            time.getString(i),
                            temperature_2m_max.getJsonNumber(i).doubleValue(),
                            temperature_2m_min.getJsonNumber(i).doubleValue(),
                            rain_sum.getJsonNumber(i).doubleValue(),
                            wind_speed_10m_max.getJsonNumber(i).doubleValue()
                    );
                    dailyAndHourlyForecasts.add(dailyAndHourlyForecast);
                }
            }
            response.close();
            client.close();
        }


        return new ForeCast(location, timezone, foreCastUnit, dailyAndHourlyForecasts);
    }

    public ForeCast getHourlyForecast(Integer locationId, String date) {

        Location location = locationService.getLocationById(locationId);
        String timezone = "";
        ForeCastUnit foreCastUnit = null;
        List<DailyAndHourlyForecast> dailyAndHourlyForecasts = new ArrayList<>();

        if (location != null) {
            Client client = ClientBuilder.newClient();
            String URL = FORECAST_BASE_URL + "?latitude=" + location.getLatitude()
                    + "&longitude=" + location.getLongitude()
                    + "&hourly=temperature_2m,rain,wind_speed_10m&start_date=" + date + "&end_date=" + date;
            LOGGER.info(URL);

            WebTarget target = client.target(URL);

            Response response = target.request(MediaType.APPLICATION_JSON).get();


            if (response.getStatus() == 200) {
                String jsonResponse = response.readEntity(String.class);
                JsonReader jsonReader = jakarta.json.Json.createReader(new StringReader(jsonResponse));
                JsonObject jsonObject = jsonReader.readObject();

                timezone = jsonObject.getString("timezone");

                JsonObject daily_units = jsonObject.getJsonObject("hourly_units");
                foreCastUnit = new ForeCastUnit(
                        daily_units.getString("time"),
                        daily_units.getString("temperature_2m"),
                        daily_units.getString("rain"),
                        daily_units.getString("wind_speed_10m")
                );

                JsonObject daily = jsonObject.getJsonObject("hourly");
                JsonArray time = daily.getJsonArray("time");
                JsonArray temperature_2m = daily.getJsonArray("temperature_2m");
                JsonArray rain = daily.getJsonArray("rain");
                JsonArray wind_speed_10m = daily.getJsonArray("wind_speed_10m");

                for (int i = 0; i < time.size(); i++) {
                    DailyAndHourlyForecast dailyAndHourlyForecast = new DailyAndHourlyForecast(
                            time.getString(i),
                            temperature_2m.getJsonNumber(i).doubleValue(),
                            rain.getJsonNumber(i).doubleValue(),
                            wind_speed_10m.getJsonNumber(i).doubleValue()
                    );
                    dailyAndHourlyForecasts.add(dailyAndHourlyForecast);
                }
            }
            response.close();
            client.close();
        }
        return new ForeCast(location, timezone, foreCastUnit, dailyAndHourlyForecasts);
    }


}
