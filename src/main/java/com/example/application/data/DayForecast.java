package com.example.application.data;

public class DayForecast {
    String date;
    Double temp;
    
    public DayForecast(String date, Double temp) {
        this.date = date;
        this.temp = temp;
    }
    public String getDate() {
        return date;
    }
    public Double getTemp() {
        return temp;
    }
}
