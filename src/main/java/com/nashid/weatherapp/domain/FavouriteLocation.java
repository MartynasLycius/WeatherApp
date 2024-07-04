package com.nashid.weatherapp.domain;

public class FavouriteLocation {

    private Long id;
    private String latitude;
    private String longitude;
    private String location;
    private String country;
    private Long userId;

    public FavouriteLocation() {
    }

    public FavouriteLocation(Long id, String latitude, String longitude, String location, String country, Long userId) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.country = country;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLabelText() {
        String label = getLocation();
        if (getCountry() != null || getCountry() != "") {
            label = label.concat(", ").concat(getCountry());
        }
        return label;
    }
}
