package com.hiddenhopes.WeatherApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeeklyData {
    private List<String> time;
    @JsonProperty("temperature_2m_max")
    private List<Double> temperature2mMax;
    @JsonProperty("temperature_2m_min")
    private List<Double> temperature2mMin;
    @JsonProperty("rain_sum")
    private List<Double> rainSum;
    @JsonProperty("windspeed_10m_max")
    private List<Double> windspeed10mMax;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperature2mMax() {
        return temperature2mMax;
    }

    public void setTemperature2mMax(List<Double> temperature2mMax) {
        this.temperature2mMax = temperature2mMax;
    }

    public List<Double> getTemperature2mMin() {
        return temperature2mMin;
    }

    public void setTemperature2mMin(List<Double> temperature2mMin) {
        this.temperature2mMin = temperature2mMin;
    }

    public List<Double> getRainSum() {
        return rainSum;
    }

    public void setRainSum(List<Double> rainSum) {
        this.rainSum = rainSum;
    }

    public List<Double> getWindspeed10mMax() {
        return windspeed10mMax;
    }

    public void setWindspeed10mMax(List<Double> windspeed10mMax) {
        this.windspeed10mMax = windspeed10mMax;
    }
}
