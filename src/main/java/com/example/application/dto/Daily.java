package com.example.application.dto;

import java.util.ArrayList;

public class Daily {
    private ArrayList<String> time;
    private ArrayList<Double> temperature_2m_max;
    private ArrayList<Double> temperature_2m_min;
    private ArrayList<Double> rain_sum;
    private ArrayList<Double> windspeed_10m_max;

    public Daily() {
    }

    public Daily(ArrayList<String> time, ArrayList<Double> temperature_2m_max, ArrayList<Double> temperature_2m_min,
                 ArrayList<Double> rain_sum, ArrayList<Double> windspeed_10m_max) {
        this.time = time;
        this.temperature_2m_max = temperature_2m_max;
        this.temperature_2m_min = temperature_2m_min;
        this.rain_sum = rain_sum;
        this.windspeed_10m_max = windspeed_10m_max;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public ArrayList<Double> getTemperature_2m_max() {
        return temperature_2m_max;
    }

    public void setTemperature_2m_max(ArrayList<Double> temperature_2m_max) {
        this.temperature_2m_max = temperature_2m_max;
    }

    public ArrayList<Double> getTemperature_2m_min() {
        return temperature_2m_min;
    }

    public void setTemperature_2m_min(ArrayList<Double> temperature_2m_min) {
        this.temperature_2m_min = temperature_2m_min;
    }

    public ArrayList<Double> getRain_sum() {
        return rain_sum;
    }

    public void setRain_sum(ArrayList<Double> rain_sum) {
        this.rain_sum = rain_sum;
    }

    public ArrayList<Double> getWindspeed_10m_max() {
        return windspeed_10m_max;
    }

    public void setWindspeed_10m_max(ArrayList<Double> windspeed_10m_max) {
        this.windspeed_10m_max = windspeed_10m_max;
    }
}
