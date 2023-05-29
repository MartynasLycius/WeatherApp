package com.example.application.dto;

import java.util.ArrayList;

public class Hourly {
    private ArrayList<String> time;
    private ArrayList<Double> temperature_2m;
    private ArrayList<Integer> relativehumidity_2m;
    private ArrayList<Double> windspeed_10m;
    private ArrayList<Double> rain;

    public Hourly() {
    }

    public Hourly(ArrayList<String> time, ArrayList<Double> temperature_2m, ArrayList<Integer> relativehumidity_2m,
                  ArrayList<Double> windspeed_10m, ArrayList<Double> rain) {
        this.time = time;
        this.temperature_2m = temperature_2m;
        this.relativehumidity_2m = relativehumidity_2m;
        this.windspeed_10m = windspeed_10m;
        this.rain = rain;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public ArrayList<Double> getTemperature_2m() {
        return temperature_2m;
    }

    public void setTemperature_2m(ArrayList<Double> temperature_2m) {
        this.temperature_2m = temperature_2m;
    }

    public ArrayList<Integer> getRelativehumidity_2m() {
        return relativehumidity_2m;
    }

    public void setRelativehumidity_2m(ArrayList<Integer> relativehumidity_2m) {
        this.relativehumidity_2m = relativehumidity_2m;
    }

    public ArrayList<Double> getWindspeed_10m() {
        return windspeed_10m;
    }

    public void setWindspeed_10m(ArrayList<Double> windspeed_10m) {
        this.windspeed_10m = windspeed_10m;
    }

    public ArrayList<Double> getRain() {
        return rain;
    }

    public void setRain(ArrayList<Double> rain) {
        this.rain = rain;
    }
}
