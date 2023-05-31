package com.proit.application.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class WeatherCodeLookupUtil {
    private static final Map<Integer, String> weatherCodesMessageMap;
    private static final Map<Integer, String> weatherCodesIconMap;

    static {
        weatherCodesMessageMap = createWeatherCodesMessageMap();
        weatherCodesIconMap = createWeatherCodesIconMap();
    }

    private static Map<Integer, String> createWeatherCodesMessageMap() {
        log.info("Creating weather codes message map");

        var map = new HashMap<Integer, String>();
        map.put(0, "Clear Sky");
        map.put(1, "Mainly Clear");
        map.put(2, "Partly Cloudy");
        map.put(3, "Overcast");

        map.put(45, "Fog");
        map.put(48, "Depositing Rime Fog");

        map.put(51, "Light Drizzle");
        map.put(53, "Moderate Drizzle");
        map.put(55, "Dense Drizzle");
        map.put(56, "Light Freezing Drizzle");
        map.put(57, "Dense Freezing Drizzle");

        map.put(61, "Slight Rain");
        map.put(63, "Moderate Rain");
        map.put(65, "Heavy Rain");
        map.put(66, "Light Freezing Rain");
        map.put(67, "Heavy Freezing Rain");

        map.put(71, "Slight Snowfall");
        map.put(73, "Moderate Snowfall");
        map.put(75, "Heavy Snowfall");
        map.put(77, "Snow Grains");

        map.put(80, "Slight Rain Showers");
        map.put(81, "Moderate Rain Showers");
        map.put(82, "Violent Rain Showers");

        map.put(85, "Slight Snow Showers");
        map.put(86, "Heavy Snow Showers");

        map.put(95, "Slight or Moderate Thunderstorm");
        map.put(96, "Thunderstorm with Slight Hail");
        map.put(99, "Thunderstorm with Heavy Hail");

        return map;
    }

    private static Map<Integer, String> createWeatherCodesIconMap() {
        log.info("Creating weather codes icon map");

        var map = new HashMap<Integer, String>();
        map.put(0, "fa fa-solid fa-sun sunny");
        map.put(1, "fa fa-solid fa-sun sunny");
        map.put(2, "fa-solid fa-cloud-sun");
        map.put(3, "fa-solid fa-cloud");

        map.put(45, "fa-solid fa-smog");
        map.put(48, "fa-solid fa-smog");

        map.put(51, "fa-solid fa-droplet");
        map.put(53, "fa-solid fa-droplet");
        map.put(55, "fa-solid fa-droplet");
        map.put(56, "fa-solid fa-droplet");
        map.put(57, "fa-solid fa-droplet");

        map.put(61, "fa-solid fa-cloud-rain");
        map.put(63, "fa-solid fa-cloud-rain");
        map.put(65, "fa-solid fa-cloud-rain");
        map.put(66, "fa-solid fa-cloud-showers-heavy");
        map.put(67, "fa-solid fa-cloud-showers-heavy");

        map.put(71, "fa-solid fa-snowflake");
        map.put(73, "fa-solid fa-snowflake");
        map.put(75, "fa-solid fa-snowflake");
        map.put(77, "fa-solid fa-snowflake");

        map.put(80, "fa-solid fa-cloud-showers-water");
        map.put(81, "fa-solid fa-cloud-showers-water");
        map.put(82, "fa-solid fa-cloud-showers-water");

        map.put(85, "fa-solid fa-solid fa-snowflake");
        map.put(86, "fa-solid fa-solid fa-snowflake");

        map.put(95, "fa-solid fa-cloud-bolt");
        map.put(96, "fa-solid fa-cloud-bolt");
        map.put(99, "fa-solid fa-cloud-bolt");

        return map;
    }

    public static String getWeatherMessage(int weatherCode) {
        log.debug("Getting weather message for weather code: {}", weatherCode);

        return weatherCodesMessageMap.get(weatherCode);
    }

    public static String getWeatherIcon(int weatherCode) {
        log.debug("Getting weather icon for weather code: {}", weatherCode);

        return weatherCodesIconMap.get(weatherCode);
    }

    private WeatherCodeLookupUtil() {

    }
}
