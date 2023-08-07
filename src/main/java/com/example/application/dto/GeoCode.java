package com.example.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoCode {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("latitude")
    private Long latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("elevation")
    private Long elevation;

    @JsonProperty("feature_code")
    private String featureCode;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("admin1_id")
    private Long admin1Id;

    @JsonProperty("admin2_id")
    private Long admin2Id;

    @JsonProperty("admin3_id")
    private Long admin3Id;

    @JsonProperty("admin4_id")
    private Long admin4Id;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("population")
    private Long population;

    @JsonProperty("country_id")
    private Long countryId;

    @JsonProperty("country")
    private String country;

    @JsonProperty("admin1")
    private String admin1;

    @JsonProperty("admin2")
    private String admin2;

    @JsonProperty("admin3")
    private String admin3;

    @JsonProperty("admin4")
    private String admin4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getElevation() {
        return elevation;
    }

    public void setElevation(Long elevation) {
        this.elevation = elevation;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Long getAdmin1Id() {
        return admin1Id;
    }

    public void setAdmin1Id(Long admin1Id) {
        this.admin1Id = admin1Id;
    }

    public Long getAdmin2Id() {
        return admin2Id;
    }

    public void setAdmin2Id(Long admin2Id) {
        this.admin2Id = admin2Id;
    }

    public Long getAdmin3Id() {
        return admin3Id;
    }

    public void setAdmin3Id(Long admin3Id) {
        this.admin3Id = admin3Id;
    }

    public Long getAdmin4Id() {
        return admin4Id;
    }

    public void setAdmin4Id(Long admin4Id) {
        this.admin4Id = admin4Id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
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

    public String getAdmin2() {
        return admin2;
    }

    public void setAdmin2(String admin2) {
        this.admin2 = admin2;
    }

    public String getAdmin3() {
        return admin3;
    }

    public void setAdmin3(String admin3) {
        this.admin3 = admin3;
    }

    public String getAdmin4() {
        return admin4;
    }

    public void setAdmin4(String admin4) {
        this.admin4 = admin4;
    }
}
