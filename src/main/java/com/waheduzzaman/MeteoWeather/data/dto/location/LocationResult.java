package com.waheduzzaman.MeteoWeather.data.dto.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LocationResult {
    @JsonProperty("results")
    private List<Location> locations;

    public List<Location> getLocations(){
        return (locations == null) ? List.of() : locations;
    }
}