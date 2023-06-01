package com.proit.application.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class WeatherCodeLookupUtil {
    private static final Map<Integer, String> weatherCodesMessageMap;
    private static final Map<Integer, List<String>> weatherCodesIconMap;

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

    private static Map<Integer, List<String>> createWeatherCodesIconMap() {
        log.info("Creating weather codes icon map");

        var map = new HashMap<Integer, List<String>>();
        map.put(0, List.of("day.svg", "night.svg"));
        map.put(1, List.of("cloudy-day-1.svg", "cloudy-night-1.svg"));
        map.put(2, List.of("cloudy-day-2.svg", "cloudy-night-2.svg"));
        map.put(3, List.of("cloudy.svg"));

        map.put(45, List.of("cloudy.svg"));
        map.put(48, List.of("cloudy.svg"));

        map.put(51, List.of("rainy-4.svg"));
        map.put(53, List.of("rainy-5.svg"));
        map.put(55, List.of("rainy-5.svg"));
        map.put(56, List.of("rainy-6.svg"));
        map.put(57, List.of("rainy-7.svg"));

        map.put(61, List.of("rainy-4.svg"));
        map.put(63, List.of("rainy-4.svg"));
        map.put(65, List.of("rainy-5.svg"));
        map.put(66, List.of("rainy-6.svg"));
        map.put(67, List.of("rainy-7.svg"));

        map.put(71, List.of("snowy-4.svg"));
        map.put(73, List.of("snowy-4.svg"));
        map.put(75, List.of("snowy-5.svg"));
        map.put(77, List.of("snowy-6.svg"));

        map.put(80, List.of("rainy-6.svg"));
        map.put(81, List.of("rainy-7.svg"));
        map.put(82, List.of("rainy-7.svg"));

        map.put(85, List.of("snowy-6.svg"));
        map.put(86, List.of("snowy-7.svg"));

        map.put(95, List.of("thunder.svg"));
        map.put(96, List.of("thunder.svg"));
        map.put(99, List.of("thunder.svg"));

        return map;
    }

    public static String getWeatherMessage(int weatherCode) {
        log.debug("Getting weather message for weather code: {}", weatherCode);

        return weatherCodesMessageMap.get(weatherCode);
    }

    public static List<String> getWeatherIconsForWeatherCode(int weatherCode) {
        log.debug("Getting weather icons for weather code: {}", weatherCode);

        return weatherCodesIconMap.get(weatherCode);
    }

    private WeatherCodeLookupUtil() {

    }
}
