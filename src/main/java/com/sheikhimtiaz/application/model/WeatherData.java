package com.sheikhimtiaz.application.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WeatherData {
    // Daily weather data
    private List<Double> dailyTemperatureMax;
    private List<Double> dailyTemperatureMin;

    // Hourly weather data
    private List<String> hourlyTime;
    private List<Double> hourlyTemperature;
    private List<Double> hourlyWindspeed;
    private List<Double> hourlyPrecipitation;

    // Convert to WeatherDaily list
    public List<WeatherDaily> getDailyWeatherData() {
        List<WeatherDaily> dailyWeatherList = new ArrayList<>();
        for (int i = 0; i < dailyTemperatureMax.size(); i++) {
            WeatherDaily daily = new WeatherDaily();
            daily.setDate("Day " + (i + 1));
            daily.setTemperatureMax(dailyTemperatureMax.get(i));
            daily.setTemperatureMin(dailyTemperatureMin.get(i));
            dailyWeatherList.add(daily);
        }
        return dailyWeatherList;
    }

    // Convert to WeatherHourly list for a specific date
    public List<WeatherHourly> getHourlyWeatherDataForDate(String date) {
        List<WeatherHourly> hourlyWeatherList = new ArrayList<>();
        // Assuming hourlyTime list has 24 entries per day
        int startIndex = (Integer.parseInt(date.split(" ")[1]) - 1) * 24;
        for (int i = startIndex; i < startIndex + 24; i++) {
            WeatherHourly hourly = new WeatherHourly();
            hourly.setTime(hourlyTime.get(i));
            hourly.setTemperature(hourlyTemperature.get(i));
            hourly.setWindspeed(hourlyWindspeed.get(i));
            hourly.setPrecipitation(hourlyPrecipitation.get(i));
            hourlyWeatherList.add(hourly);
        }
        return hourlyWeatherList;
    }

    // Getters and Setters
    public List<Double> getDailyTemperatureMax() {
        return dailyTemperatureMax;
    }

    public void setDailyTemperatureMax(List<Double> dailyTemperatureMax) {
        this.dailyTemperatureMax = dailyTemperatureMax;
    }

    public List<Double> getDailyTemperatureMin() {
        return dailyTemperatureMin;
    }

    public void setDailyTemperatureMin(List<Double> dailyTemperatureMin) {
        this.dailyTemperatureMin = dailyTemperatureMin;
    }

    public List<String> getHourlyTime() {
        return hourlyTime;
    }

    public void setHourlyTime(List<String> hourlyTime) {
        this.hourlyTime = hourlyTime;
    }

    public List<Double> getHourlyTemperature() {
        return hourlyTemperature;
    }

    public void setHourlyTemperature(List<Double> hourlyTemperature) {
        this.hourlyTemperature = hourlyTemperature;
    }

    public List<Double> getHourlyWindspeed() {
        return hourlyWindspeed;
    }

    public void setHourlyWindspeed(List<Double> hourlyWindspeed) {
        this.hourlyWindspeed = hourlyWindspeed;
    }

    public List<Double> getHourlyPrecipitation() {
        return hourlyPrecipitation;
    }

    public void setHourlyPrecipitation(List<Double> hourlyPrecipitation) {
        this.hourlyPrecipitation = hourlyPrecipitation;
    }
}
