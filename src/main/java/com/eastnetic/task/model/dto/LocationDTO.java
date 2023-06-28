package com.eastnetic.task.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    @JsonProperty("results")
    List<LocationResults> results;

    @JsonProperty("generationtime_ms")
    double generationtimeMs;
}
