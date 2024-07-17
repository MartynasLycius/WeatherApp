package org.weather.app.models;

import java.util.List;

public class CurrentForecastCustom {

    private List<DailyForecast> dailyForecastList;
    private List<HourlyRain> hourlyRainList;
    private CurrentWeather currentWeather;

    public List<DailyForecast> getDailyForecastList() {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }

    public CurrentForecastCustom(List<DailyForecast> dailyForecastList, List<HourlyRain> hourlyRainList) {
        this.dailyForecastList = dailyForecastList;
        this.hourlyRainList = hourlyRainList;
    }
    public CurrentForecastCustom(CurrentWeather currentWeather, List<DailyForecast> dailyForecastList, List<HourlyRain> hourlyRainList) {
        this.dailyForecastList = dailyForecastList;
        this.hourlyRainList = hourlyRainList;
        this.currentWeather = currentWeather;
    }

    public List<HourlyRain> getHourlyRainList() {
        return hourlyRainList;
    }

    public void setHourlyRainList(List<HourlyRain> hourlyRainList) {
        this.hourlyRainList = hourlyRainList;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }
}
