package com.example.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoCodeResult {

    @JsonProperty("results")
    GeoCode[] results;

    @JsonProperty("generationtime_ms")
    private String generationtimeMs;

    public GeoCode[] getResults() {
        return results;
    }

    public void setResults(GeoCode[] results) {
        this.results = results;
    }

    public String getGenerationtimeMs() {
        return generationtimeMs;
    }

    public void setGenerationtimeMs(String generationtimeMs) {
        this.generationtimeMs = generationtimeMs;
    }
}
