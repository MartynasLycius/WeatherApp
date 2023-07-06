package com.waheduzzaman.MeteoWeather.service.impl;

import com.waheduzzaman.MeteoWeather.data.dto.weather.Hourly;
import com.waheduzzaman.MeteoWeather.data.dto.weather.Weather;
import com.waheduzzaman.MeteoWeather.data.model.ui.HourlyForecastItemForListViewModel;
import com.waheduzzaman.MeteoWeather.data.model.ui.TemperatureCardViewModel;
import com.waheduzzaman.MeteoWeather.service.AbstractWeatherDetailsService;
import com.waheduzzaman.MeteoWeather.service.network.impl.WeatherAPIWrapperImpl;
import com.waheduzzaman.MeteoWeather.utility.DateTimeUtility;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WeatherDetailsServiceImpl extends AbstractWeatherDetailsService {

    public WeatherDetailsServiceImpl(WeatherAPIWrapperImpl weatherAPIWrapper) {
        super(weatherAPIWrapper);
    }

    @Override
    public Weather getWeatherDetailsFromAPI(Double longitude, Double latitude) {
        Objects.requireNonNull(longitude);
        Objects.requireNonNull(latitude);
        return weatherData = weatherAPIWrapper.callNetworkAPI(longitude, latitude);
    }

    @Override
    public Weather getWeatherData() {
        return this.weatherData;
    }

    @Override
    public List<String> getHourlyTimeStampForChart(int selectedIndex) {
        return weatherData.getHourly().getTime()
                .subList((selectedIndex > 0) ? selectedIndex * 24 : 0, (selectedIndex > 0) ? (selectedIndex * 24) + 24 : 24)
                .stream()
                .map(DateTimeUtility::getAMPMTimeFromDateString)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getHourlyTemperatureListForChart(int selectedIndex) {
        return weatherData.getHourly().getTemperature2m().subList((selectedIndex > 0) ? selectedIndex * 24 : 0, (selectedIndex > 0) ? (selectedIndex * 24) + 24 : 24)
                .stream()
                .map(Object::toString).toList();
    }

    @Override
    public TemperatureCardViewModel getTemperatureCardViewModel(int selectedIndex) {
        return new TemperatureCardViewModel(
                weatherData.getDaily().getTemperature2mMax().get(selectedIndex),
                weatherData.getDaily().getTemperature2mMin().get(selectedIndex),
                weatherData.getDailyUnits().getTemperature2mMax()
        );
    }

    @Override
    public List<HourlyForecastItemForListViewModel> getHourlyForecastItemForListView(int index) {
        List<HourlyForecastItemForListViewModel> listItems = new ArrayList<>();
        Hourly hourly = weatherData.getHourly();
        for (int startingIndex = (index > 0) ? index * 24 : 0;
             startingIndex < ((index > 0) ? (index * 24) + 24 : 24);
             startingIndex++) {
            listItems.add(new HourlyForecastItemForListViewModel(
                    DateTimeUtility.getAMPMTimeFromDateString(hourly.getTime().get(startingIndex)),
                    hourly.getTemperature2m().get(startingIndex),
                    hourly.getPrecipitationProbability().get(startingIndex),
                    hourly.getRain().get(startingIndex),
                    hourly.getWindspeed10m().get(startingIndex)
            ));
        }
        return listItems;
    }
}
