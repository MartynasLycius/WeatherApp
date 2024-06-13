package com.weatherapp.detail;

import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 09 Jun, 2024
 */
@lombok.Getter
@lombok.Setter
public class LocationResponse {
    private List<LocationInformation> results;
    private double generationtime_ms;
}
