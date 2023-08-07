package com.eastnetic.application.weathers.serviceImpl;

import com.eastnetic.application.weathers.entity.WeatherData;
import com.eastnetic.application.weathers.service.WeatherProviderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@CacheConfig(cacheNames = "weatherDataCache")
public class OpenMeteoWeatherProviderServiceImpl implements WeatherProviderService {

    private static final Logger LOGGER = LogManager.getLogger(OpenMeteoWeatherProviderServiceImpl.class);

    private static final String API_BASE_URL = "https://api.open-meteo.com/v1/forecast?";

    private final RestTemplate restTemplate;

    @Autowired
    public OpenMeteoWeatherProviderServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String buildApiUrl(double latitude, double longitude, String timezone) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(API_BASE_URL)
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("timezone", timezone)
                .queryParam("current_weather", true);

        return builder.toUriString();
    }

    @Override
    @Cacheable(key = "'daily-weather-' + #latitude + '-' + #longitude + '-' + #timezone", unless = "#result == null")
    public WeatherData getDailyWeatherData(double latitude, double longitude, String timezone) {

        String apiUrl = buildApiUrl(latitude, longitude, timezone) +
                "&daily=temperature_2m_max,temperature_2m_min,sunrise,sunset,rain_sum,windspeed_10m_max";

        LOGGER.info("Fetching daily weather data from open-meteo api: latitude={}, longitude={}, timezone={}: START",
                latitude, longitude, timezone);

        try {

            WeatherData weatherData = restTemplate.getForObject(apiUrl, WeatherData.class);

            LOGGER.info("Fetching daily weather data from open-meteo api: latitude={}, longitude={}, timezone={}: SUCCESS",
                    latitude, longitude, timezone);

            return weatherData;

        } catch (Exception ex) {

            LOGGER.error("Fetching daily weather data from open-meteo api: latitude={}, longitude={}, timezone={}: Error",
                    latitude, longitude, timezone, ex);
        }

        return null;
    }

    @Override
    public WeatherData getHourlyWeatherDataOfADay(double latitude, double longitude, String timezone, LocalDate date) {

        String dateString = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

        String apiUrl = buildApiUrl(latitude, longitude, timezone) +
                "&start_date=" + dateString + "&end_date=" + dateString +
                "&hourly=temperature_2m,rain,windspeed_10m,winddirection_10m";

        LOGGER.info("Fetching hourly weather data from open-meteo api: latitude={}, longitude={}, timezone={}, date={}: START",
                latitude, longitude, timezone, date);

        try {

            WeatherData weatherData = restTemplate.getForObject(apiUrl, WeatherData.class);

            LOGGER.info("Fetching hourly weather data from open-meteo api: latitude={}, longitude={}, timezone={},  date={}: SUCCESS",
                    latitude, longitude, timezone, date);

            return weatherData;

        } catch (Exception ex) {

            LOGGER.error("Fetching hourly weather data from open-meteo api: latitude={}, longitude={}, timezone={}, date={}: Error",
                    latitude, longitude, timezone, date, ex);
        }

        return null;
    }
}