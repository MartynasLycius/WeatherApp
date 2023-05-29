package com.weather.app.constants;

public class Endpoints {
    public static final String LOGIN = "/login";
    public static final String REGISTRATION = "/api/user/register";;
    public static final String API_USER = "/api/user";
    public static final String API_USER_REGISTER = "/register";
    public static final String API_LOCATION = "/api/location";
    public static final String API_LOCATION_CITY_PATH_VARIABLE = "/{city}";
    public static final String API_UPDATE_FAVOURITE_LOCATION = "/favourite/add/{userId}";
    public static final String ENDPOINT_PATTERN = "/**";

    public static final String API_WEATHER = "/api/weather";
    public static final String API_DAILY_FORECAST="/daily";
    public static final String API_HOURLY_FORECAST = "/hourly";
    public static final String API_GET_USER_BY_ID = "/{id}";
    public static final String API_LOCATION_BY_ID = "/byId/{locationId}";
    public static final String API_GET_FAVOURITE_LOCATION = "/favourite/get/{userId}";
}
