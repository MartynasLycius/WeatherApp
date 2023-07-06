package com.waheduzzaman.MeteoWeather.data.dto.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "latitude",
        "longitude",
        "country_code",
        "timezone",
        "country_id",
        "country",
})

public class Location {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("latitude")
    private Double latitude;
    @JsonProperty("longitude")
    private Double longitude;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("country_id")
    private Integer countryId;
    @JsonProperty("country")
    private String country;

}