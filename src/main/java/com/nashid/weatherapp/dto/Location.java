package com.nashid.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("name")
    private String location;
    @JsonProperty("country_code")
    private String countryCode;
    private String country;
    private String latitude;
    private String longitude;

    private CurrentWeatherResult weatherResult;

    public Location() {}

    public Location(String location, String countryCode, String country, String latitude, String longitude) {
        this.location = location;
        this.countryCode = countryCode;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public CurrentWeatherResult getWeatherResult() {
        return weatherResult;
    }

    public void setWeatherResult(CurrentWeatherResult weatherResult) {
        this.weatherResult = weatherResult;
    }

    public Integer getWeatherCode() {
        return weatherResult.getCurrent().getWeather_code();
    }

    public String getTemperature() {
        return weatherResult.getCurrent().getTemperature_2m().toString().concat(" ").concat(weatherResult.getCurrent_units().getTemperature_2m());
    }

    public String getSurfaceWind() {
        return weatherResult.getCurrent().getWind_speed_10m().toString().concat(" ").concat(weatherResult.getCurrent_units().getWind_speed_10m());
    }

    public String getRain() {
        return weatherResult.getCurrent().getRain().toString().concat(" ").concat(weatherResult.getCurrent_units().getRain());
    }

    public String getLastUpdatedTime() {
        return weatherResult.getCurrent().getTime().replace("T", " ");
    }

    public String getLabelText() {
        String label = getLocation();
        if (getCountry() != null || getCountry() != "") {
            label = label.concat(", ").concat(getCountry());
        }
        return label;
    }
}
