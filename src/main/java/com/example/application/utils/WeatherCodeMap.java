package com.example.application.utils;

import java.util.HashMap;
import java.util.Map;


public final class WeatherCodeMap {

    public static Map<Integer, String> weatherCodesMessageMap = new HashMap<>() {{
        put(0, "Clear Sky");
        put(1, "Mainly Clear");
        put(2, "Partly Cloudy");
        put(3, "Overcast");
        put(45, "Fog");
        put(48, "Depositing Rime Fog");
        put(51, "Light Drizzle");
        put(53, "Moderate Drizzle");
        put(55, "Dense Drizzle");
        put(56, "Light Freezing Drizzle");
        put(57, "Dense Freezing Drizzle");
        put(61, "Slight Rain");
        put(63, "Moderate Rain");
        put(65, "Heavy Rain");
        put(66, "Light Freezing Rain");
        put(67, "Heavy Freezing Rain");

        put(71, "Slight Snowfall");
        put(73, "Moderate Snowfall");
        put(75, "Heavy Snowfall");
        put(77, "Snow Grains");

        put(80, "Slight Rain Showers");
        put(81, "Moderate Rain Showers");
        put(82, "Violent Rain Showers");

        put(85, "Slight Snow Showers");
        put(86, "Heavy Snow Showers");

        put(95, "Slight or Moderate Thunderstorm");
        put(96, "Thunderstorm with Slight Hail");
        put(99, "Thunderstorm with Heavy Hail");
    }};


    public static Map<Integer, String> weatherCodesIconMap = new HashMap<>() {{
        put(0, "fa fa-solid fa-sun sunny");
        put(1, "fa fa-solid fa-sun sunny");
        put(2, "fa-solid fa-cloud-sun");
        put(3, "fa-solid fa-cloud");

        put(45, "fa-solid fa-smog");
        put(48, "fa-solid fa-smog");

        put(51, "fa-solid fa-droplet");
        put(53, "fa-solid fa-droplet");
        put(55, "fa-solid fa-droplet");
        put(56, "fa-solid fa-droplet");
        put(57, "fa-solid fa-droplet");

        put(61, "fa-solid fa-cloud-rain");
        put(63, "fa-solid fa-cloud-rain");
        put(65, "fa-solid fa-cloud-rain");
        put(66, "fa-solid fa-cloud-showers-heavy");
        put(67, "fa-solid fa-cloud-showers-heavy");

        put(71, "fa-solid fa-snowflake");
        put(73, "fa-solid fa-snowflake");
        put(75, "fa-solid fa-snowflake");
        put(77, "fa-solid fa-snowflake");

        put(80, "fa-solid fa-cloud-showers-water");
        put(81, "fa-solid fa-cloud-showers-water");
        put(82, "fa-solid fa-cloud-showers-water");

        put(85, "fa-solid fa-solid fa-snowflake");
        put(86, "fa-solid fa-solid fa-snowflake");

        put(95, "fa-solid fa-cloud-bolt");
        put(96, "fa-solid fa-cloud-bolt");
        put(99, "fa-solid fa-cloud-bolt");

    }};


    public static String getWeatherMessage(int weatherCode) {
        return weatherCodesMessageMap.get(weatherCode);
    }

    public static String getWeatherIcon(int weatherCode) {
        return weatherCodesIconMap.get(weatherCode);
    }
}
