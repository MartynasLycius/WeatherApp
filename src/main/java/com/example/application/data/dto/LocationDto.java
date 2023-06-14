package com.example.application.data.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationDto {

    public Long id;
    public String name;
    public Double latitude;
    public Double longitude;
    public Double elevation;
    public String featureCode;
    public String countryCode;
    public int population;
    public int countryId;
    public String timezone;
    public String country;
    public String admin1;
    public String admin2;
    public String admin3;
    public String admin4;

    private boolean isFavourite;
    private String address;
}
