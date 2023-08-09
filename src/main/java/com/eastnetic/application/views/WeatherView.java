package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.weathers.entity.WeatherData;
import com.eastnetic.application.weathers.service.WeatherProviderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Route(value = "weather", layout = MainLayout.class)
@PageTitle("Weather Information")
public class WeatherView extends VerticalLayout implements BeforeEnterObserver {

    private LocationDetails location;

    private final WeatherProviderService weatherProviderService;

    public WeatherView(WeatherProviderService weatherProviderService) {

        addClassName("weather-view");

        this.weatherProviderService = weatherProviderService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        event.getNavigationTarget();
        location = (LocationDetails) UI.getCurrent().getSession().getAttribute("selectedLocation");

        if (location != null) {

            updateWeatherData();
        }
    }

    private void updateWeatherData() {

        WeatherData weatherData = weatherProviderService.getDailyWeatherData(
                location.getLatitude(), location.getLongitude(), location.getTimezone()
        );

        CurrentWeatherCard weatherCard = new CurrentWeatherCard(
                weatherData.getCurrentWeather().getTemperature(),
                weatherData.getCurrentWeather().getWindSpeed(),
                location.locationFullName(),
                weatherData.getCurrentWeather().getTime()
        );

        add(weatherCard);
    }
}
