package com.tanmoy.weatherapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityId;

    private String cityName;
    private double latitude;
    private double longitude;
    private String countryName;

}
