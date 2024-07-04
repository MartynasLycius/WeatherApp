package com.nashid.weatherapp.enums;

public enum TemperatureUnit {
    celsius("Celsius °C"), fahrenheit("Fahrenheit °F");

    private String value;

    TemperatureUnit(String value) {
        this.value = value;
    }

    public String toString(){
        return value;
    }
}
