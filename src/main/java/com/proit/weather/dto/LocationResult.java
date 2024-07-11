package com.proit.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocationResult {
    @JsonProperty("results")
    private List<LocationInfoDto> results = new ArrayList<>();
}
