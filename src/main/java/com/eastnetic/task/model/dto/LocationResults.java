package com.eastnetic.task.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationResults {

    @JsonProperty("id")
    int id;

    @JsonProperty("name")
    String name;

    @JsonProperty("latitude")
    double latitude;

    @JsonProperty("longitude")
    double longitude;

    @JsonProperty("elevation")
    int elevation;

    @JsonProperty("feature_code")
    String featureCode;

    @JsonProperty("country_code")
    String countryCode;

    @JsonProperty("admin1_id")
    int admin1Id;

    @JsonProperty("admin2_id")
    int admin2Id;

    @JsonProperty("admin3_id")
    int admin3Id;

    @JsonProperty("admin4_id")
    int admin4Id;

    @JsonProperty("timezone")
    String timezone;

    @JsonProperty("population")
    int population;

    @JsonProperty("country_id")
    int countryId;

    @JsonProperty("country")
    String country;

    @JsonProperty("admin1")
    String admin1;

    @JsonProperty("admin2")
    String admin2;

    @JsonProperty("admin3")
    String admin3;

    @JsonProperty("admin4")
    String admin4;
}
