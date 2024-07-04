package com.nashid.weatherapp.enums;

public enum PrecipitationUnit {
    mm("Millimeter"), inch("Inch");

    private String value;

    PrecipitationUnit(String value) {
        this.value = value;
    }

    public String toString(){
        return value;
    }
}
