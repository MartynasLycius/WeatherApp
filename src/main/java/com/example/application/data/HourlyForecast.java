package com.example.application.data;

import jakarta.persistence.Embeddable;

@Embeddable
public class HourlyForecast
{
    private String time;
    private String temperature;
    private String rain;
    private String windSpeed;

    public HourlyForecast()
    {
    }


    public HourlyForecast(String time, String temperature, String rain, String windSpeed)
    {
        this.time = time;
        this.temperature = temperature;
        this.rain = rain;
        double windSpeedKmhDouble = Double.parseDouble(windSpeed);
        double windSpeedMsDouble = windSpeedKmhDouble * 5 / 18; // convert km/h to m/s
        this.windSpeed = String.format("%.2f", windSpeedMsDouble);
    }

    // getters and Setters
    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public String getRain()
    {
        return rain;
    }

    public void setRain(String rain)
    {
        this.rain = rain;
    }

    public String getWindSpeed()
    {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed)
    {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString()
    {
        return "HourlyForecast{" +
                "time='" + time + '\'' +
                ", temperature=" + temperature +
                ", rain=" + rain +
                ", windSpeed=" + windSpeed +
                '}';
    }
}
