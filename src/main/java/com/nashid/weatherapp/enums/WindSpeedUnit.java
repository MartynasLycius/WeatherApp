package com.nashid.weatherapp.enums;

public enum WindSpeedUnit {
    kmh("Km/h"), ms("m/s"), mph("Mph"), kn("Knots");

    private String value;

    WindSpeedUnit(String value) {
        this.value = value;
    }

    public String toString(){
        return value;
    }
}
