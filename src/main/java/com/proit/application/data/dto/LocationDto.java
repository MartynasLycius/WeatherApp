package com.proit.application.data.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LocationDto implements Serializable {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String address;
    private String country;
    private String countryCode;
    private boolean isFavourite;
}
