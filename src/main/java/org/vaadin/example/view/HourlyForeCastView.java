package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;
import org.vaadin.example.SecuredLayout;
import org.vaadin.example.entity.DailyAndHourlyForecast;
import org.vaadin.example.entity.ForeCast;
import org.vaadin.example.entity.ForeCastUnit;
import org.vaadin.example.entity.Location;
import org.vaadin.example.service.ForecastService;
import org.vaadin.tatu.BeanTable;

import java.util.List;

import static org.vaadin.example.util.ForecastLocation.getLocationLayout;

@Route(value="location/:locationId/forecast/:date", layout = SecuredLayout.class)
public class HourlyForeCastView extends VerticalLayout implements SecuredView, BeforeEnterObserver {
    @Inject
    ForecastService forecastService;
    private Integer locationId;
    private String date;

    private ForeCast foreCast;
    BeanTable<DailyAndHourlyForecast> beanTable = new BeanTable<>(DailyAndHourlyForecast.class, false, 10);
    public HourlyForeCastView() {

    }

    public void init() {
        H3 pageTitle = new H3("Hourly Forecast");
        foreCast = forecastService.getHourlyForecast(locationId, date);

        Location location = foreCast.getLocation();
        ForeCastUnit foreCastUnit = foreCast.getForeCastUnit();
        List<DailyAndHourlyForecast> dailyAndHourlyForecasts = foreCast.getDailyAndHourlyForecasts();

        H5 locationLabel = new H5("Location");
        locationLabel.getStyle().set("margin-top","2em");
        HorizontalLayout locationLayout = getLocationLayout(location);


        beanTable.addColumn("Time (" + foreCast.getTimezone() + ")", DailyAndHourlyForecast::getTime);
        beanTable.addColumn("Temperature (" + foreCastUnit.getTemperature_2m() + ")", DailyAndHourlyForecast::getTemperature_2m);
        beanTable.addColumn("Surface Wind (" + foreCastUnit.getWind_speed_10m() + ")", DailyAndHourlyForecast::getWind_speed_10m);
        beanTable.addColumn("Rain (" + foreCastUnit.getRain() + ")", DailyAndHourlyForecast::getRain);


        beanTable.setWidthFull();
        beanTable.setItems(dailyAndHourlyForecasts);

        add(pageTitle, locationLabel, locationLayout,beanTable);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String locationIdParameter = event.getRouteParameters().get("locationId").orElse(null);
        String dateParameter = event.getRouteParameters().get("date").orElse(null);

        if (locationIdParameter != null) {
            try {
                this.locationId = Integer.parseInt(locationIdParameter);
            } catch (NumberFormatException e) {
                // Show an error notification if the locationId is not a valid integer
                Notification.show("Invalid location ID: " + locationIdParameter, 3000, Notification.Position.BOTTOM_CENTER);
                this.locationId = null;
            }
        }
        if (dateParameter != null) {
            try {
                this.date = dateParameter;
            } catch (NumberFormatException e) {
                // Show an error notification if the locationId is not a valid integer
                Notification.show("Invalid date: " + dateParameter, 3000, Notification.Position.BOTTOM_CENTER);
                this.date = null;
            }
        }
        // Initialize the view
        init();
    }
}
