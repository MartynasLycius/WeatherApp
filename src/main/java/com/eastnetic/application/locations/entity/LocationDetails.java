package com.eastnetic.application.locations.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationDetails {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("elevation")
    private double elevation;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("country")
    private String country;

    @JsonProperty("admin1")
    private String admin1;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAdmin1() {
        return admin1;
    }

    public void setAdmin1(String admin1) {
        this.admin1 = admin1;
    }

    public String locationFullName() {

        if (getAdmin1() == null || getAdmin1().trim().isEmpty()) {
            return getName() + ", " + getCountry();
        }

        return getName() + ", " + getAdmin1() + ", " + getCountry();
    }
}
