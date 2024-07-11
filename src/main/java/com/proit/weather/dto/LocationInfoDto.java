package com.proit.weather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LocationInfoDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("elevation")
    private Double elevation;

    @JsonProperty("feature_code")
    private String featureCode;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("population")
    private Integer population;

    @JsonProperty("country_id")
    private Integer countryId;

    @JsonProperty("country")
    private String country;

    @JsonProperty(value = "postcodes")
    private List<String> postcodes;
}
