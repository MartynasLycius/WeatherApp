package com.proit.weather.proit.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FavoriteLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userId;
    private float latitude;
    private float longitude;
    private Double elevation;
    private Integer population;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
    private String featureCode;

    // Getters and setters...
}

