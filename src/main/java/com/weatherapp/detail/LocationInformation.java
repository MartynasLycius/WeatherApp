package com.weatherapp.detail;

import lombok.Data;

import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 09 Jun, 2024
 */
@Data
public class LocationInformation {
    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private double elevation;
    private String feature_code;
    private String country_code;
    private long admin1_id;
    private long admin3_id;
    private long admin4_id;
    private String timezone;
    private int population;
    private List<String> postcodes;
    private long country_id;
    private String country;
    private String admin1;
    private String admin3;
    private String admin4;
    private boolean isFavorite;



}
