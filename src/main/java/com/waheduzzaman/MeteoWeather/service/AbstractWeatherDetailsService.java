package com.waheduzzaman.MeteoWeather.service;

import com.waheduzzaman.MeteoWeather.data.dto.weather.Weather;
import com.waheduzzaman.MeteoWeather.data.model.ui.HourlyForecastItemForListViewModel;
import com.waheduzzaman.MeteoWeather.data.model.ui.TemperatureCardViewModel;
import com.waheduzzaman.MeteoWeather.service.network.impl.WeatherAPIWrapperImpl;

import java.util.List;

public abstract class AbstractWeatherDetailsService {
    protected Weather weatherData;
    protected WeatherAPIWrapperImpl weatherAPIWrapper;

    public AbstractWeatherDetailsService(WeatherAPIWrapperImpl weatherAPIWrapper) {
        this.weatherAPIWrapper = weatherAPIWrapper;
    }

    public abstract Weather getWeatherData();

    public abstract Weather getWeatherDetailsFromAPI(Double longitude, Double latitude);

    public abstract List<String> getHourlyTimeStampForChart(int selectedIndex);

    public abstract List<String> getHourlyTemperatureListForChart(int selectedIndex);

    public abstract TemperatureCardViewModel getTemperatureCardViewModel(int selectedIndex);

    public abstract List<HourlyForecastItemForListViewModel> getHourlyForecastItemForListView(int index);
}
