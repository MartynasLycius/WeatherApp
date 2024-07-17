package org.weather.app.views;


import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.weather.app.Utilities.VaadinUtils;
import org.weather.app.components.*;
import org.weather.app.entities.FavoriteCity;
import org.weather.app.entities.User;
import org.weather.app.models.*;
import org.weather.app.services.FavoriteService;
import org.weather.app.services.CurrentForecastService;


import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Route("current-forecast/:country/:city/:latitude/:longitude")
public class CurrentForecastView extends AbstractView implements BeforeEnterObserver, CurrentForecast {

    @Inject
    private CurrentForecastService currentForecastService;
    @Inject
    private FavoriteService favoriteService;
    private String latitude;
    private String longitude;

    private CurrentForecastComponent currentForecastComponent;
    private CurrentRainForecastComponent currentRainForecastComponent;
    private DailyForecastComponent dailyForecastComponent;
    private HorizontalLayout currentForecastLayout;
    private VerticalLayout rainForecastLayout;
    private VerticalLayout dailyForecastLayout;
    private User loginUser;
    private boolean isFavouriteCity = false;
    private String country;
    private String city;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        HttpServletRequest request = VaadinUtils.getCurrentHttpRequest();
        if (request != null && request.getAttribute("user") == null) {
            event.rerouteTo("login");
        }
        loginUser = (User) request.getAttribute("user");
        latitude = event.getRouteParameters().get("latitude").orElse("Unknown");
        longitude = event.getRouteParameters().get("longitude").orElse("Unknown");
        country = event.getRouteParameters().get("country").orElse("Unknown");
        city = event.getRouteParameters().get("city").orElse("Unknown");
        init();
    }

    public void init(){
        currentForecastService = new CurrentForecastService();
        CompletableFuture<CurrentForecastCustom> completableFuture = currentForecastService.fetchLocationDetails(latitude, longitude);
        CurrentForecastCustom currentForecastCustom = null;
        try {
            currentForecastCustom = completableFuture.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        currentForecastLayout = currentForecastComponent.getCurrentForecastLayout();
        rainForecastLayout = currentRainForecastComponent.getRainForecastLayout();
        dailyForecastLayout = dailyForecastComponent.getDailyForecastLayout();
        fillComponent(currentForecastCustom);
    }

    @Override
    public void initializeComponents() {
        currentForecastComponent = new CurrentForecastComponent(this);
        currentRainForecastComponent = new CurrentRainForecastComponent();
        dailyForecastComponent = new DailyForecastComponent();
    }

    private void fillComponent(CurrentForecastCustom currentForecastCustom){
        currentForecastComponent.fillCurrentForecast(country, city, currentForecastCustom.getCurrentWeather());
        currentRainForecastComponent.fillRainForecast(currentForecastCustom.getHourlyRainList(), currentForecastCustom.getCurrentWeather().getIsDay());
        dailyForecastComponent.fillDailyForecast(country, city, latitude, longitude, currentForecastCustom.getCurrentWeather().getIsDay(), currentForecastCustom.getDailyForecastList());
        add(currentForecastLayout, rainForecastLayout, dailyForecastLayout);
    }

    @Override
    public void generateComponents() {

    }

    @Override
    public void clickOnFavorite() {
        FavoriteCity favoriteCity = new FavoriteCity();
        favoriteCity.setId(UUID.randomUUID());
        favoriteCity.setUser(loginUser);
        favoriteCity.setLatitude(latitude);
        favoriteCity.setLongitude(longitude);
        favoriteCity.setCountry(country);
        favoriteCity.setName("Dhaka");

        if(isFavouriteCity){
            favoriteService.delete(favoriteCity);
            currentForecastComponent.removeFavorite();
            NotificationComponent.createNotification("Remove from favorite", "success").open();
        }else {
            favoriteService.save(favoriteCity);
            currentForecastComponent.addToFavorite();
            NotificationComponent.createNotification("Add to favorite", "success").open();
        }
    }
}
