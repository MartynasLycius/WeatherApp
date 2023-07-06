package com.waheduzzaman.MeteoWeather.data.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity(name = "favourite_location")
@Data
@RequiredArgsConstructor
public class FavouriteLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String country;
    private Double latitude;
    private Double longitude;
    @ManyToOne
    private User user;
}
