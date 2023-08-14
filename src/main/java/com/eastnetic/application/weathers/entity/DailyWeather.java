package com.eastnetic.application.weathers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DailyWeather {

    @JsonProperty("time")
    private List<String> time;

    @JsonProperty("temperature_2m_max")
    private List<Double> maxTemperature;

    @JsonProperty("temperature_2m_min")
    private List<Double> minTemperature;

    @JsonProperty("sunrise")
    private List<String> sunrise;

    @JsonProperty("sunset")
    private List<String> sunset;

    @JsonProperty("rain_sum")
    private List<Double> rainSum;

    @JsonProperty("windspeed_10m_max")
    private List<Double> maxWindSpeed;

    public DailyWeather() {}

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(List<Double> maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public List<Double> getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(List<Double> minTemperature) {
        this.minTemperature = minTemperature;
    }

    public List<String> getSunrise() {
        return sunrise;
    }

    public void setSunrise(List<String> sunrise) {
        this.sunrise = sunrise;
    }

    public List<String> getSunset() {
        return sunset;
    }

    public void setSunset(List<String> sunset) {
        this.sunset = sunset;
    }

    public List<Double> getRainSum() {
        return rainSum;
    }

    public void setRainSum(List<Double> rainSum) {
        this.rainSum = rainSum;
    }

    public List<Double> getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(List<Double> maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }
}
