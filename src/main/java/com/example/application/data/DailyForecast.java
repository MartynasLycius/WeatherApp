package com.example.application.data;

import jakarta.persistence.Embeddable;

import java.time.LocalDate;


@Embeddable
public class DailyForecast
{
    private String date;
    private double maxTemperature;
    private double minTemperature;

    public DailyForecast()
    {
    }

    public DailyForecast(String date, double maxTemperature, double minTemperature)
    {
        this.date = date;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
    }

    // getters and Setters
    public String getDate()
    {
        return date;
    }


    public void setDate(String date)
    {
        this.date = date;
    }

    public double getMaxTemperature()
    {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature)
    {
        this.maxTemperature = maxTemperature;
    }

    public double getMinTemperature()
    {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature)
    {
        this.minTemperature = minTemperature;
    }

    // toString() method (optional, for easy debugging)
    @Override
    public String toString()
    {
        return "DailyForecast{" +
                "date='" + date + '\'' +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                '}';
    }
}
