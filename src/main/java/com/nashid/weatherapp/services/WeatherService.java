package com.nashid.weatherapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpMethod;
import com.nashid.weatherapp.core.api.ApiClient;
import com.nashid.weatherapp.core.api.WeatherApi;
import com.nashid.weatherapp.domain.FavouriteLocation;
import com.nashid.weatherapp.domain.Settings;
import com.nashid.weatherapp.dto.CurrentWeatherResult;
import com.nashid.weatherapp.dto.HourlyWeatherResult;
import com.nashid.weatherapp.dto.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WeatherService {

    @Inject
    private SettingsService settingsService;
    public CurrentWeatherResult getCurrentWeather(String latitude, String longitude) {
        Settings settings = settingsService.getCurrentUserSettings();
        String weatherParam = "&current=temperature_2m,wind_speed_10m,rain,weather_code&timeformat=iso8601&timezone=" + settings.getTimeZone().toString() + "&wind_speed_unit=" + settings.getWindSpeedUnit().name() + "&precipitation_unit=" + settings.getPrecipitationUnit().name() + "&temperature_unit=" + settings.getTemperatureUnit().name();
        String weatherData = ApiClient.callUrl(WeatherApi.WEATHER_API_FORECAST_URL.concat("?latitude=").concat(latitude).concat("&longitude=").concat(longitude).concat(weatherParam), HttpMethod.GET);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            CurrentWeatherResult weatherResult = objectMapper.readValue(weatherData, CurrentWeatherResult.class);
            return weatherResult;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Location getLocationFromFavouriteLocation(FavouriteLocation favouriteLocation) {
        Settings settings = settingsService.getCurrentUserSettings();
        String weatherParam = "&current=temperature_2m,wind_speed_10m,rain,weather_code&timeformat=iso8601&timezone=" + settings.getTimeZone().toString() + "&wind_speed_unit=" + settings.getWindSpeedUnit().name() + "&precipitation_unit=" + settings.getPrecipitationUnit().name() + "&temperature_unit=" + settings.getTemperatureUnit().name();
        String weatherData = ApiClient.callUrl(WeatherApi.WEATHER_API_FORECAST_URL.concat("?latitude=").concat(favouriteLocation.getLatitude()).concat("&longitude=").concat(favouriteLocation.getLongitude()).concat(weatherParam), HttpMethod.GET);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Location location = new Location();
            CurrentWeatherResult weatherResult = objectMapper.readValue(weatherData, CurrentWeatherResult.class);
            location.setLocation(favouriteLocation.getLocation());
            location.setCountryCode("");
            location.setCountry(favouriteLocation.getCountry());
            location.setLatitude(favouriteLocation.getLatitude());
            location.setLongitude(favouriteLocation.getLongitude());
            location.setWeatherResult(weatherResult);
            return location;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public HourlyWeatherResult getHourlyWeather(String latitude, String longitude) {
        Settings settings = settingsService.getCurrentUserSettings();
        String weatherParam = "&forecast_days=1&hourly=temperature_2m,wind_speed_10m,rain,weather_code&timeformat=iso8601&timezone=" + settings.getTimeZone().toString() + "&wind_speed_unit=" + settings.getWindSpeedUnit().name() + "&precipitation_unit=" + settings.getPrecipitationUnit().name() + "&temperature_unit=" + settings.getTemperatureUnit().name();
        String weatherData = ApiClient.callUrl(WeatherApi.WEATHER_API_FORECAST_URL.concat("?latitude=").concat(latitude).concat("&longitude=").concat(longitude).concat(weatherParam), HttpMethod.GET);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            HourlyWeatherResult weatherResult = objectMapper.readValue(weatherData, HourlyWeatherResult.class);
            return weatherResult;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Settings getCurrentUserSettings() {
        return settingsService.getCurrentUserSettings();
    }
}
