package com.example.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
@Entity
@Table(name = "favourites")
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Integer id;
    private String location_id;
    private String name;
    private String user_id;
    private String latitude;
    private String longitude;

    public Favourites() {
    }
    public String getLocationId() {
        return location_id;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getName() {
        return name;
    }
    public String getLongitude() {
        return longitude;
    }
    public Integer getId() {
        return id;
    }

}
