package com.hiddenhopes.WeatherApp;

public class Constant {
    public static final String GEOCODING_API_URL = "https://geocoding-api.open-meteo.com/v1/search";
    public static final String API_URL = "https://api.open-meteo.com/v1/forecast?";
    public static final String DAILY_PARAMS = "&daily=temperature_2m_max,temperature_2m_min,rain_sum,windspeed_10m_max&timezone=auto";
    public static final String HOURLY_PARAMS = "&hourly=temperature_2m,rain,windspeed_10m";
}
