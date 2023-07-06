package com.waheduzzaman.MeteoWeather.views.home;

import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.config.AppRoutes;
import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import com.waheduzzaman.MeteoWeather.data.entity.FavouriteLocation;
import com.waheduzzaman.MeteoWeather.interfaces.AddClickListener;
import com.waheduzzaman.MeteoWeather.interfaces.OnGEOValueChangedListener;
import com.waheduzzaman.MeteoWeather.service.impl.FavouriteLocationServiceImpl;
import com.waheduzzaman.MeteoWeather.service.impl.WeatherDetailsServiceImpl;
import com.waheduzzaman.MeteoWeather.utility.WMOCodeToOpenWeatherIcon;
import com.waheduzzaman.MeteoWeather.views.MainLayout;
import com.waheduzzaman.MeteoWeather.views.components.FavouriteLocationGridWidget;
import com.waheduzzaman.MeteoWeather.views.components.GEOComponent;
import com.waheduzzaman.MeteoWeather.views.components.IconTextBoxWidget;
import jakarta.annotation.security.PermitAll;
import org.apache.commons.lang3.StringUtils;
import org.vaadin.elmot.flow.sensors.Position;
import org.vaadin.elmot.flow.sensors.PositionErrorEvent;

@PermitAll
@PageTitle("Home | Meteo Weather")
@Route(value = AppRoutes.HOME_ROUTE, layout = MainLayout.class)
public class HomeView extends HorizontalLayout {

    private final VerticalLayout leftPanel = new VerticalLayout();
    private GEOComponent geoComponent;
    private FavouriteLocationGridWidget favouriteLocationGridWidget;
    private WeatherDetailsView weatherDetailsView;
    private FavouriteLocationServiceImpl favouriteLocationService;
    private WMOCodeToOpenWeatherIcon codeToOpenWeatherIcon;
    private WeatherDetailsServiceImpl weatherDetailsService;

    private H4 locationLabel;

    public HomeView(FavouriteLocationServiceImpl favouriteLocationService, GEOComponent geoComponent, WMOCodeToOpenWeatherIcon codeToOpenWeatherIcon, WeatherDetailsServiceImpl weatherDetailsService) {
        AppRoutes.setCurrentPath(AppRoutes.HOME_ROUTE);
        initObjects(favouriteLocationService, geoComponent, codeToOpenWeatherIcon, weatherDetailsService);
        setSizeFull();
        initLeftPanel();

        if (AppRoutes.getSessionValue("location") != null) {
            Location location = (Location) AppRoutes.getSessionValue("location");
            updateShowingLocationLabel(location.getName());
            showWeatherDetails(location.getLongitude(), location.getLatitude());
        } else {
            addGeoComponent();
        }

    }

    private void addGeoComponent() {
        add(geoComponent.getGEOView(new OnGEOValueChangedListener() {
            @Override
            public void onSuccess(Position position) {
                favouriteLocationGridWidget.reloadData();
            }

            @Override
            public void onError(PositionErrorEvent pee) {
                Notification.show("geo location error: " + pee.getMessage()).addThemeVariants(NotificationVariant.LUMO_ERROR);
                favouriteLocationGridWidget.assertUserCanContinue();
            }
        }));
    }

    private void showWeatherDetails(Double longitude, Double latitude) {
        if (weatherDetailsView != null)
            remove(weatherDetailsView);
        weatherDetailsService.getWeatherDetailsFromAPI(longitude, latitude);
        weatherDetailsView = new WeatherDetailsView(this.weatherDetailsService, this.codeToOpenWeatherIcon);
        add(weatherDetailsView);
    }

    private void initLeftPanel() {
        leftPanel.add(locationLabel);
        leftPanel.setClassName(LumoUtility.Background.BASE);
        leftPanel.add(new IconTextBoxWidget("Search Location", "search", new AddClickListener() {
            @Override
            public <T> void onItemClick(T item) {
                AppRoutes.openPage(AppRoutes.INDEX_ROUTE);
            }
        }));
        leftPanel.add(favouriteLocationGridWidget);
        leftPanel.setWidth("20%");
        add(leftPanel);
    }

    private void initObjects(FavouriteLocationServiceImpl favouriteLocationService, GEOComponent geoComponent, WMOCodeToOpenWeatherIcon codeToOpenWeatherIcon, WeatherDetailsServiceImpl weatherDetailsService) {
        this.favouriteLocationService = favouriteLocationService;
        this.codeToOpenWeatherIcon = codeToOpenWeatherIcon;
        this.geoComponent = geoComponent;
        this.weatherDetailsService = weatherDetailsService;
        this.locationLabel = new H4();
        this.favouriteLocationGridWidget = new FavouriteLocationGridWidget(this.favouriteLocationService, new AddClickListener() {
            @Override
            public <T> void onItemClick(T item) {
                FavouriteLocation location = (FavouriteLocation) item;
                showWeatherDetails(location.getLongitude(), location.getLatitude());
            }
        });
    }

    private void updateShowingLocationLabel(String label) {
        if (StringUtils.isEmpty(label))
            locationLabel.setText(" ");
        else
            locationLabel.setText("showing: " + label);
    }
}
