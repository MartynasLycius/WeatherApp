package org.weather.app.views;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.weather.app.Utilities.VaadinUtils;
import org.weather.app.components.HourlyForecastComponent;
import org.weather.app.models.HourlyForecast;
import org.weather.app.services.HourlyForecastService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Route("hourly-forecast/:country/:city/:latitude/:longitude/:date")
public class HourlyForecastView extends AbstractView implements BeforeEnterObserver {

    private String latitude;
    private String longitude;
    private String date;
    private String country;
    private String city;
    @Inject
    private HourlyForecastService hourlyForecastService;
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        HttpServletRequest request = VaadinUtils.getCurrentHttpRequest();
        if (request != null && request.getAttribute("user") == null) {
            event.rerouteTo("login");
        }
        latitude = event.getRouteParameters().get("latitude").orElse("Unknown");
        longitude = event.getRouteParameters().get("longitude").orElse("Unknown");
        date = event.getRouteParameters().get("date").orElse("Unknown");
        country = event.getRouteParameters().get("country").orElse("Unknown");
        city = event.getRouteParameters().get("city").orElse("Unknown");
        fillComponents();
    }

    public void fillComponents(){

        CompletableFuture<List<HourlyForecast>> completableFuture = hourlyForecastService.fetchHourlyForecast(latitude, longitude, date, date);
        try {
            List<HourlyForecast> hourlyForecastList = completableFuture.get();
            if(null != hourlyForecastList){
                for (HourlyForecast hourlyForecast : hourlyForecastList) {
                    HourlyForecastComponent hourlyForecastComponent = new HourlyForecastComponent(country, city);
                    hourlyForecastComponent.loadHourlyForecast(hourlyForecast);
                    add(hourlyForecastComponent.getContentLayout());
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generateComponents() {

    }

    @Override
    public void initializeComponents() {
        hourlyForecastService = new HourlyForecastService();
    }
}
