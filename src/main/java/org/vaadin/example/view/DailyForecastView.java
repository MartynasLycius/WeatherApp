package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.vaadin.example.SecuredLayout;
import org.vaadin.example.entity.DailyAndHourlyForecast;
import org.vaadin.example.entity.ForeCast;
import org.vaadin.example.entity.ForeCastUnit;
import org.vaadin.example.entity.Location;
import org.vaadin.example.service.ForecastService;
import org.vaadin.example.util.LabelValueUtils;
import org.vaadin.tatu.BeanTable;

import java.util.List;

import static org.vaadin.example.util.ForecastLocation.getLocationLayout;
import static org.vaadin.example.util.LabelValueUtils.createLabelValueComponent;


@Route(value="location/:locationId/forecast", layout = SecuredLayout.class)
public class DailyForecastView extends VerticalLayout implements SecuredView, BeforeEnterObserver {

    @Inject
    ForecastService forecastService;
    private Integer locationId;
    private ForeCast foreCast;
    BeanTable<DailyAndHourlyForecast> beanTable = new BeanTable<>(DailyAndHourlyForecast.class, false, 10);
    public DailyForecastView() {

    }
    public void init() {
        H3 pageTitle = new H3("Daily Forecast");
        foreCast = forecastService.getDailyForCast(locationId, 7);

        Location location = foreCast.getLocation();
        ForeCastUnit foreCastUnit = foreCast.getForeCastUnit();
        List<DailyAndHourlyForecast> dailyAndHourlyForecasts = foreCast.getDailyAndHourlyForecasts();

        H5 locationLabel = new H5("Location");
        locationLabel.getStyle().set("margin-top","2em");
        HorizontalLayout locationLayout = getLocationLayout(location);

        Select<Integer> forecastDaysSelect = getForecastDaysSelect();


        beanTable.addColumn("Time (" + foreCast.getTimezone() + ")", DailyAndHourlyForecast::getTime);
        beanTable.addColumn("Max Temperature (" + foreCastUnit.getTemperature_2m_max() + ")", DailyAndHourlyForecast::getTemperature_2m_max);
        beanTable.addColumn("Min Temperature (" + foreCastUnit.getTemperature_2m_min() + ")", DailyAndHourlyForecast::getTemperature_2m_min);
        beanTable.addColumn("Surface Wind (" + foreCastUnit.getWind_speed_10m_max() + ")", DailyAndHourlyForecast::getWind_speed_10m_max);
        beanTable.addColumn("Total Rain (" + foreCastUnit.getRain_sum() + ")", DailyAndHourlyForecast::getRain_sum);
        beanTable.addComponentColumn("Action", dailyAndHourlyForecast -> {
            Button viewButton = new Button("Hourly Forecast");
            viewButton.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate("location/" + locationId + "/forecast/" + dailyAndHourlyForecast.getTime()));
            });
            return viewButton;
        }).setHeader("Action");

        beanTable.setWidthFull();
        beanTable.setItems(dailyAndHourlyForecasts);

        add(pageTitle, locationLabel, locationLayout, forecastDaysSelect,beanTable);
    }

    private Select<Integer> getForecastDaysSelect() {
        Select<Integer> forecastDaysSelect = new Select<>();
        forecastDaysSelect.setLabel("Forecast Length");
        forecastDaysSelect.setItems(1, 3, 7, 14, 16);
        forecastDaysSelect.setItemLabelGenerator(days -> days + " day" + (days > 1 ? "s" : ""));
        forecastDaysSelect.setValue(7); // Set default value to 7 days

        forecastDaysSelect.addValueChangeListener(event -> {
            Integer selectedDays = event.getValue();
            if (selectedDays == null)
                selectedDays = 7;

             foreCast = forecastService.getDailyForCast(locationId, selectedDays);
             beanTable.setItems(foreCast.getDailyAndHourlyForecasts());

        });
        return forecastDaysSelect;
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String locationIdParameter = event.getRouteParameters().get("locationId").orElse(null);
        if (locationIdParameter != null) {
            try {
                this.locationId = Integer.parseInt(locationIdParameter);
            } catch (NumberFormatException e) {
                // Show an error notification if the locationId is not a valid integer
                Notification.show("Invalid location ID: " + locationIdParameter, 3000, Notification.Position.BOTTOM_CENTER);
                this.locationId = null;
            }
        }
        // Initialize the view
        init();
    }
}
