package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationModel {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    private Double elevation;
    private String country_code;
    private String timezone;
    private Long population;
    private Long country_id;
    private String country;

}
