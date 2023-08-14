package com.eastnetic.application.locations.response;


import com.eastnetic.application.locations.entity.LocationDetails;

import java.util.List;

public class OpenMeteoLocationApiResponse {

    private List<LocationDetails> results;

    private double generationtime_ms;

    public List<LocationDetails> getResults() {
        return results;
    }

    public void setResults(List<LocationDetails> results) {
        this.results = results;
    }

    public double getGenerationtime_ms() {
        return generationtime_ms;
    }

    public void setGenerationtime_ms(double generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }
}
