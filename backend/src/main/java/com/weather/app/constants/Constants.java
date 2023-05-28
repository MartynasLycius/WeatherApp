package com.weather.app.constants;

import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

public class Constants {

    public static final long EXIPIRATION_TIME = 86400000;
    public static final String SECURITY_KEY = "635266556A586E3272357538782F413F4428472B4B6250655367566B59703373";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String BEARER = "Bearer ";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CITY_PARAM = "city_name";
    public static final String LOCATION_BASE_URL = "https://geocoding-api.open-meteo.com";
    public static final String LOCATION_URI =
            "/v1/search?name=city_name&count=10&language=en&format=json";
    public static final String ID_PARAM = "ID_VALUE";
    public static final String LOCATION_URI_BY_ID = "/v1/get?id=ID_VALUE";
    public static final String WEATHER_BASE_URL =  "https://api.open-meteo.com";
    public static final String LATITUDE_PARAM = "LATITUDE_VALUE";
    public static final String LONGITUDE_PARAM = "LONGITUDE_VALUE";
    public static final String TIMEZONE_PARAM = "TIMEZONE_VALUE";
    public static final String WEATHER_URI_DAILY =
            "/v1/forecast?latitude=LATITUDE_VALUE&longitude=LONGITUDE_VALUE&timezone=TIMEZONE_VALUE&daily=temperature_2m_max,temperature_2m_min,rain_sum,windspeed_10m_max,windgusts_10m_max";
    public static final String WEATHER_URI_HOURLY =
            "/v1/forecast?latitude=LATITUDE_VALUE&longitude=LONGITUDE_VALUE&hourly=temperature_2m,rain,surface_pressure,windspeed_10m,winddirection_10m";

    public static final String ERROR_MESSAGE_KEY = "errorMessage";
}