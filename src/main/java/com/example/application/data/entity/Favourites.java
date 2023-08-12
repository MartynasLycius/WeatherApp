package com.example.application.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Favourites extends AbstractEntity {

    @NotEmpty
    @Column(name = "user_id")
    private String userId;

    @NotNull
    @Column(name = "geo_code_id")
    private Long geoCodeId;

    @NotEmpty
    @Column(name = "city_name")
    private String cityName;

    @NotEmpty
    @Column(name = "country_name")
    private String countryName;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @NotNull
    @Column(name = "is_favourite")
    private int isFavourite;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getGeoCodeId() {
        return geoCodeId;
    }

    public void setGeoCodeId(Long geoCodeId) {
        this.geoCodeId = geoCodeId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }
}
