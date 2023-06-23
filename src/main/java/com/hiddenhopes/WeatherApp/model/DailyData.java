package com.hiddenhopes.WeatherApp.model;
public class DailyData {
    private String time;
    private double temperature2mMax;
    private double temperature2mMin;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTemperature2mMax() {
        return temperature2mMax;
    }

    public void setTemperature2mMax(double temperature2mMax) {
        this.temperature2mMax = temperature2mMax;
    }

    public double getTemperature2mMin() {
        return temperature2mMin;
    }

    public void setTemperature2mMin(double temperature2mMin) {
        this.temperature2mMin = temperature2mMin;
    }

    public double getRainSum() {
        return rainSum;
    }

    public void setRainSum(double rainSum) {
        this.rainSum = rainSum;
    }

    public double getWindspeed10mMax() {
        return windspeed10mMax;
    }

    public void setWindspeed10mMax(double windspeed10mMax) {
        this.windspeed10mMax = windspeed10mMax;
    }

    private double rainSum;
    private double windspeed10mMax;
}
