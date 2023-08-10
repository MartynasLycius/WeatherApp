package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.weathers.entity.DailyWeather;
import com.eastnetic.application.weathers.entity.WeatherData;
import com.eastnetic.application.weathers.service.WeatherProviderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;
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

    private final H3 locationName = new H3();

    private final CurrentWeatherCard currentWeatherCard;

    private final WeatherProviderService weatherProviderService;

    public WeatherView(CurrentWeatherCard currentWeatherCard,
                       WeatherProviderService weatherProviderService) {

        this.currentWeatherCard = currentWeatherCard;
        this.weatherProviderService = weatherProviderService;

        add(locationName, currentWeatherCard);
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

        locationName.setText(location.locationFullName());

        currentWeatherCard.showCurrentWeather(weatherData.getCurrentWeather());

        DailyWeather dailyWeather = weatherData.getDailyWeather();

        int dayCount = dailyWeather.getTime().size();

        for (int i=0; i<dayCount; i++) {

            DailyWeatherCard dailyWeatherCard = new DailyWeatherCard(
                    dailyWeather.getTime().get(i),
                    dailyWeather.getMaxTemperature().get(i),
                    dailyWeather.getMinTemperature().get(i),
                    dailyWeather.getSunrise().get(i),
                    dailyWeather.getSunset().get(i),
                    dailyWeather.getRainSum().get(i),
                    dailyWeather.getMaxWindSpeed().get(i),
                    location,
                    weatherProviderService
            );

            add(dailyWeatherCard);
        }
    }
}
