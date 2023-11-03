package com.example.application.data;

import jakarta.persistence.*;

import java.util.List;


@Entity
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String locationName = "";
    private String timezone = "";
    private String latitude = "";
    private String longitude = "";
    private String country = "";


    @ElementCollection(fetch = FetchType.EAGER)
    private List<DailyForecast> dailyForecasts;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<HourlyForecast> hourlyForecasts;


    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }


    public Location()
    {

    }

    public Location(String locationName)
    {
        this.locationName = locationName;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }


    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone(String timezone)
    {
        this.timezone = timezone;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts)
    {
        this.dailyForecasts = dailyForecasts;
    }

    public List<DailyForecast> getDailyForecasts()
    {
        return dailyForecasts;
    }

    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts)
    {
        this.hourlyForecasts = hourlyForecasts;
    }

    public List<HourlyForecast> getHourlyForecasts()
    {
        return hourlyForecasts;
    }
}
