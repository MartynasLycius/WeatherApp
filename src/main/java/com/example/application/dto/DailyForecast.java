package com.example.application.dto;

public class DailyForecast {
    private String date;
    private Double minTemperature;
    private Double maxTemperature;
    private Double rainSum;
    private Double maxWindSpeed;

    public DailyForecast() {
    }

    public DailyForecast(String date, Double minTemperature, Double maxTemperature, Double rainSum, Double maxWindSpeed) {
        this.date = date;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.rainSum = rainSum;
        this.maxWindSpeed = maxWindSpeed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getRainSum() {
        return rainSum;
    }

    public void setRainSum(Double rainSum) {
        this.rainSum = rainSum;
    }

    public Double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(Double maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }
}
