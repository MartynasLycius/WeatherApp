package com.eastnetic.application.locations.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "locations", uniqueConstraints = @UniqueConstraint(columnNames={"reference_id", "reference_source"}))
public class Location {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "reference_id", columnDefinition = "VARCHAR(100)")
    private String referenceId;

    @Column(name = "reference_source", columnDefinition = "VARCHAR(100)")
    private String referenceSource;

    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Column(name = "elevation", nullable = false)
    private double elevation;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "timezone", columnDefinition = "VARCHAR(100)", nullable = false)
    private String timezone;

    @Column(name = "country", columnDefinition = "VARCHAR(100)", nullable = false)
    private String country;

    @Column(name = "admin_1", columnDefinition = "VARCHAR(255)", nullable = false)
    private String admin1;

    public Location() {}

    public Location(String referenceId,
                    String referenceSource,
                    String name,
                    double latitude,
                    double longitude,
                    double elevation,
                    String countryCode,
                    String timezone,
                    String country,
                    String admin1) {

        this.referenceId = referenceId;
        this.referenceSource = referenceSource;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.countryCode = countryCode;
        this.timezone = timezone;
        this.country = country;
        this.admin1 = admin1;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceSource() {
        return referenceSource;
    }

    public void setReferenceSource(String referenceSource) {
        this.referenceSource = referenceSource;
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
}
