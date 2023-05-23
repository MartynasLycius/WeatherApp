package com.example.application.dto;

import java.util.ArrayList;

public class GeoCodingResponse {
    private ArrayList<CityGeoCoding> results;
    private double generationtime_ms;

    public GeoCodingResponse() {
    }

    public GeoCodingResponse(ArrayList<CityGeoCoding> results, double generationtime_ms) {
        this.results = results;
        this.generationtime_ms = generationtime_ms;
    }

    public ArrayList<CityGeoCoding> getResults() {
        return results;
    }

    public void setResults(ArrayList<CityGeoCoding> results) {
        this.results = results;
    }

    public double getGenerationtime_ms() {
        return generationtime_ms;
    }

    public void setGenerationtime_ms(double generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }
}
