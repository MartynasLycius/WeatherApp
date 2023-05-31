package com.proit.application.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.dto.WeatherDataDto;
import com.proit.application.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class OpenMeteoClient implements WeatherRestClient {
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${open.meteo.location.base-url}")
    private String openMeteoLocationBaseUrl;

    @Value("${open.meteo.forecast.base-url}")
    private String openMeteoForecastBaseUrl;

    @Value("${open.meteo.api.location-search}")
    private String locationSearchApiLink;

    @Value("${open.meteo.api.daily-forecast}")
    private String dailyWeatherForecastApiLink;

    public OpenMeteoClient(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<LocationDto> getLocations(String query) {
        log.info("Fetching locations for query: {}", query);

        String requestUrl = String.format(locationSearchApiLink, openMeteoLocationBaseUrl, query);
        var request = buildRequest(requestUrl);

        try (Response response = httpClient.newCall(request).execute()) {
            log.info("Successfully fetched locations for query: {}", query);
            String responseBody = Objects.requireNonNull(response.body()).string();
            return parseLocations(responseBody);
        } catch (IOException e) {
            log.error("Error while fetching locations", e);
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    @Override
    public WeatherDataDto getWeatherData(double lat, double lang, String timezone) {
        log.info("Fetching weather data for latitude: {}, longitude: {}, timezone: {}", lat, lang, timezone);

        String requestUrl = String.format(dailyWeatherForecastApiLink, openMeteoForecastBaseUrl, lat, lang, timezone);
        var request = buildRequest(requestUrl);

        try (Response response = httpClient.newCall(request).execute()) {
            log.info("Successfully fetched weather data for latitude: {}, longitude: {}, timezone: {}", lat, lang, timezone);

            var responseBodyObj = response.body();
            return responseBodyObj != null ? parseWeatherData(responseBodyObj.string()) : null;
        } catch (Exception e) {
            log.error("Error while fetching weather data", e);
            e.printStackTrace();
        }

        return null;
    }

    private WeatherDataDto parseWeatherData(String responseBody) throws JsonProcessingException {
        log.debug("Parsing weather data response: {}", responseBody);

        if (!responseBody.isEmpty()) {
            return objectMapper.readValue(responseBody, WeatherDataDto.class);
        }
        return null;
    }

    private List<LocationDto> parseLocations(String responseBody) throws JsonProcessingException {
        log.debug("Parsing locations response: {}", responseBody);

        var toReturn = new ArrayList<LocationDto>();

        if (!responseBody.isEmpty()) {
            var mainNode = objectMapper.readTree(responseBody);
            var resultsNode = mainNode.get("results");
            if (resultsNode != null) {
                for (JsonNode resultNode : resultsNode) {
                    var locationDto = LocationDto.builder()
                            .id(JsonUtil.getLong(resultNode, "id"))
                            .name(JsonUtil.getString(resultNode, "name"))
                            .latitude(JsonUtil.getDouble(resultNode, "latitude"))
                            .longitude(JsonUtil.getDouble(resultNode, "longitude"))
                            .address(JsonUtil.constructAddressFromResultNode(resultNode))
                            .country(JsonUtil.getString(resultNode, "country"))
                            .countryCode(JsonUtil.getString(resultNode, "country_code"))
                            .build();
                    toReturn.add(locationDto);
                }
            }
        }
        return toReturn;
    }

    private Request buildRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }
}
