package com.eastnetic.task.model.dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_fav_locations")
public class UserFavLocations {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "region")
    private String region;

    @Column(name = "country")
    private String country;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "raw_data_from_api", length = 65535)
    private String rawData;
}
