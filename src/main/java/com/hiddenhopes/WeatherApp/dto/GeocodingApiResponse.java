package com.hiddenhopes.WeatherApp.dto;

public class GeocodingApiResponse {
    private Location[] results;

    public Location[] getResults() {
        return results;
    }

    public void setResults(Location[] results) {
        this.results = results;
    }
}
