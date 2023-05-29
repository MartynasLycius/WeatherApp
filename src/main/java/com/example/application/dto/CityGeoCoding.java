package com.example.application.dto;

import java.util.ArrayList;

public class CityGeoCoding {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double elevation;
    private String feature_code;
    private String country_code;
    private int admin1_id;
    private int admin3_id;
    private int admin4_id;
    private String timezone;
    private int population;
    private ArrayList<String> postcodes;
    private int country_id;
    private String country;
    private String admin1;
    private String admin3;
    private String admin4;
    private int admin2_id;
    private String admin2;

    public CityGeoCoding() {
    }

    public CityGeoCoding(int id, String name, double latitude, double longitude, double elevation, String feature_code,
                         String country_code, int admin1_id, int admin3_id, int admin4_id, String timezone,
                         int population, ArrayList<String> postcodes, int country_id, String country, String admin1,
                         String admin3, String admin4, int admin2_id, String admin2) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.feature_code = feature_code;
        this.country_code = country_code;
        this.admin1_id = admin1_id;
        this.admin3_id = admin3_id;
        this.admin4_id = admin4_id;
        this.timezone = timezone;
        this.population = population;
        this.postcodes = postcodes;
        this.country_id = country_id;
        this.country = country;
        this.admin1 = admin1;
        this.admin3 = admin3;
        this.admin4 = admin4;
        this.admin2_id = admin2_id;
        this.admin2 = admin2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getFeature_code() {
        return feature_code;
    }

    public void setFeature_code(String feature_code) {
        this.feature_code = feature_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public int getAdmin1_id() {
        return admin1_id;
    }

    public void setAdmin1_id(int admin1_id) {
        this.admin1_id = admin1_id;
    }

    public int getAdmin3_id() {
        return admin3_id;
    }

    public void setAdmin3_id(int admin3_id) {
        this.admin3_id = admin3_id;
    }

    public int getAdmin4_id() {
        return admin4_id;
    }

    public void setAdmin4_id(int admin4_id) {
        this.admin4_id = admin4_id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public ArrayList<String> getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(ArrayList<String> postcodes) {
        this.postcodes = postcodes;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
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

    public int getAdmin2_id() {
        return admin2_id;
    }

    public void setAdmin2_id(int admin2_id) {
        this.admin2_id = admin2_id;
    }

    public String getAdmin2() {
        return admin2;
    }

    public void setAdmin2(String admin2) {
        this.admin2 = admin2;
    }
}
