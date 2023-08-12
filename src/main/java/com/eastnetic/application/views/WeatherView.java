package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.weathers.entity.DailyWeather;
import com.eastnetic.application.weathers.entity.WeatherData;
import com.eastnetic.application.weathers.exceptions.WeatherDataException;
import com.eastnetic.application.weathers.service.WeatherProviderService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@AnonymousAllowed
@Component
@UIScope
@Route(value = "weather", layout = MainLayout.class)
@PageTitle("Weather Information")
public class WeatherView extends VerticalLayout implements BeforeEnterObserver {

    private static final Logger LOGGER = LogManager.getLogger(WeatherView.class);

    private LocationDetails location;

    private final H3 locationName = new H3();

    private final CurrentWeatherCard currentWeatherCard;

    private final WeatherProviderService weatherProviderService;

    public WeatherView(CurrentWeatherCard currentWeatherCard,
                       WeatherProviderService weatherProviderService) {

        this.currentWeatherCard = currentWeatherCard;
        this.weatherProviderService = weatherProviderService;

        HorizontalLayout markLocation = new HorizontalLayout();
        FavoriteIconButton favoriteIcon = new FavoriteIconButton();
        markLocation.add(locationName, favoriteIcon);

        add(markLocation, currentWeatherCard);
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

        try {
            WeatherData weatherData = weatherProviderService.getDailyWeatherData(
                    location.getLatitude(), location.getLongitude(), location.getTimezone()
            );

            locationName.setText(location.locationFullName());

            currentWeatherCard.showCurrentWeather(weatherData.getCurrentWeather());

            DailyWeather dailyWeather = weatherData.getDailyWeather();

            int dayCount = dailyWeather.getTime().size();

            for (int i = 0; i < dayCount; i++) {

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

        } catch (WeatherDataException ex) {

            LOGGER.error("Error updating weather data: {}", ex.getMessage(), ex);

            Notification.show(ex.getMessage(), 3000, Notification.Position.MIDDLE);

        } catch (Exception ex) {

            LOGGER.error("Error updating weather data: {}", ex.getMessage(), ex);

            Notification.show("Error fetching weather data. Please try again later.", 3000, Notification.Position.MIDDLE);
        }
    }
}
