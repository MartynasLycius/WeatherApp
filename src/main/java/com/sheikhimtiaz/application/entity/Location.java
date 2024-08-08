package com.sheikhimtiaz.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double latitude;
    private double longitude;
    private double elevation;
    private String featureCode;
    private String countryCode;
    private long admin1Id;
    private long admin2Id;
    private long admin3Id;
    private long admin4Id;
    private String timezone;
    private int population;
    private List<String> postcodes;
    private long countryId;
    private String country;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
}
