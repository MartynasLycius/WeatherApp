package com.example.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
public class Location {
  @JsonProperty("id")
  private Integer id;

  @JsonProperty("name")
  private String name;

  @JsonProperty("latitude")
  private Double latitude;

  @JsonProperty("longitude")
  private Double longitude;

  @JsonProperty("timezone")
  private String timezone;

  @JsonProperty("country")
  private String country;

  public Integer getId() {
    return id;
  }

  public Location setMainAttributes(Integer id, String nameS, Double lat, Double longt) {
    this.id = id;
    this.name = nameS;
    this.latitude = lat;
    this.longitude = longt;
    return this;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

}

