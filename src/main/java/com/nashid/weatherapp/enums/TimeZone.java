package com.nashid.weatherapp.enums;

public enum TimeZone {
    America_Anchorage("America/Anchorage", "America/Anchorage"),
    America_Los_Angeles("America/Los_Angeles", "America/Los_Angeles"),
    America_Denver("America/Denver", "America/Denver"),
    America_Chicago("America/Chicago", "America/Chicago"),
    America_New_York("America/New_York", "America/New_York"),
    America_Sao_Paulo("America/Sao_Paulo", "America/Sao_Paulo"),
    UTC("UTC", "Not set (GMT+0)"),
    GMT("GMT", "GMT+0"),
    Europe_London("Europe/London", "Europe/London"),
    Europe_Berlin("Europe/Berlin", "Europe/Berlin"),
    Europe_Moscow("Europe/Moscow", "Europe/Moscow"),
    Africa_Cairo("Africa/Cairo", "Africa/Cairo"),
    Asia_Bangkok("Asia/Bangkok", "Asia/Bangkok"),
    Asia_Singapore("Asia/Singapore", "Asia/Singapore"),
    Asia_Tokyo("Asia/Tokyo", "Asia/Tokyo"),
    Asia_Dhaka("Asia/Dhaka", "Asia/Dhaka"),
    Australia_Sydney("Australia/Sydney", "Australia/Sydney"),
    Pacific_Auckland("Pacific/Auckland", "Pacific/Auckland");

    private String display;
    private String value;

    TimeZone(String value, String display) {
        this.value = value;
        this.display = display;
    }

    public String toString(){
        return value;
    }

    public String getDisplay(){
        return display;
    }
}
