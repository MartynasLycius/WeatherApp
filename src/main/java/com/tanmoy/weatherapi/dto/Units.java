package com.tanmoy.weatherapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Units {

    private String time;
    private String temperature_2m;
    private String relativehumidity_2m;
    private String windspeed_10m;
}
