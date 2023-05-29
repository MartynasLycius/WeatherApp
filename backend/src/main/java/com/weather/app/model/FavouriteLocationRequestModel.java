package com.weather.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavouriteLocationRequestModel {
    private Long id;
    private Long locationId;
    private String location;
    private String country;
}
