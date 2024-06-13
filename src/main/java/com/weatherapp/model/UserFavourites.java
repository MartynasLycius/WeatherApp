package com.weatherapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */
@Entity(name = "user_favourites")
@Table(name="user_favourites"
        ,catalog="weather"
        , uniqueConstraints = @UniqueConstraint(columnNames={"latitude", "longitude", "user_id"})
)
public class UserFavourites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(name = "country")
    private String country;

    @NotBlank
    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountryCity() {
        return country + " - " + city;
    }
}