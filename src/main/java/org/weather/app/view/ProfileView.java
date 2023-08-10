package org.weather.app.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.jetbrains.annotations.NotNull;
import org.weather.app.config.Constants;
import org.weather.app.config.Routes;
import org.weather.app.domain.FavouriteLocation;
import org.weather.app.service.FavouriteLocationService;
import org.weather.app.service.WeatherForecastService;
import org.weather.app.service.dto.Location;
import org.weather.app.service.dto.pojo.WmoCode;
import org.weather.app.ui.WeekForecastView;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@PermitAll
@PageTitle("Profile")
@Route(value = Routes.PROFILE_ROUTE, layout = ApplicationLayout.class)
public class ProfileView extends HorizontalLayout {

    private final VerticalLayout leftLayout = new VerticalLayout();
    private final H4 locationLabel = new H4("");
    private Grid<FavouriteLocation> favouriteLocationGrid;
    private final FavouriteLocationService favouriteLocationService;
    private final WeatherForecastService weatherForecastService;
    private WeekForecastView weekForecastView;
    private final Map<Integer, WmoCode> wmoCodeMap;

    public ProfileView(FavouriteLocationService favouriteLocationService, WeatherForecastService weatherForecastService, Map<Integer, WmoCode> wmoCodeMap) {
        this.favouriteLocationService = favouriteLocationService;
        this.weatherForecastService = weatherForecastService;
        this.wmoCodeMap = wmoCodeMap;
        Routes.setCurrentPath(Routes.PROFILE_ROUTE);
        initLeftLayout();

        Location location;
        if (Objects.nonNull(Routes.getSessionValue(Constants.SESSION_KEY))) {
            location = (Location) Routes.getSessionValue(Constants.SESSION_KEY);
            updateLocationLabel(location);
            renderWeekForecastByLocation(location);
        } else {
            List<FavouriteLocation> list = favouriteLocationService.findAllFavouriteLocationsForLoggedInUser();
            if (!list.isEmpty()) {
                location = this.buildApiLocation(list.get(0));
                updateLocationLabel(location);
                renderWeekForecastByLocation(location);
                favouriteLocationGrid.select(list.get(0));
            }
        }
    }

    private void initLeftLayout() {
        setId("ProfileView-extends-HorizontalLayout-FullLayout");
        leftLayout.setId("ProfileView-leftLayout");
        setSizeFull();
        List<FavouriteLocation> locations = favouriteLocationService.findAllFavouriteLocationsForLoggedInUser();
        favouriteLocationGrid = new Grid<>(FavouriteLocation.class);
        favouriteLocationGrid.setSizeFull();
        favouriteLocationGrid.setColumns();
        favouriteLocationGrid.addColumn(renderCompositeProperty()).setHeader("My favourite locations");
        favouriteLocationGrid.addColumn(renderAction()).setHeader("Action");
        favouriteLocationGrid.setItems(locations);
        favouriteLocationGrid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        favouriteLocationGrid.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS);
        favouriteLocationGrid.setClassName(LumoUtility.Border.NONE);
        favouriteLocationGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        favouriteLocationGrid.getElement().setAttribute("hide-header-row", true);
        favouriteLocationGrid.asSingleSelect().addValueChangeListener(event -> {
            if (Objects.nonNull(event.getValue())) {
                this.renderWeekForecastByLocation(buildApiLocation(event.getValue()));
            } else {
                remove(weekForecastView);
            }
        });

        leftLayout.add(locationLabel);
        leftLayout.setClassName(LumoUtility.Background.BASE);
        leftLayout.add(favouriteLocationGrid);
        leftLayout.setWidth("20%");
        add(leftLayout);
    }

    private void updateLocationLabel(Location location) {
        locationLabel.setText("");
        if (Objects.nonNull(location)) {
            locationLabel.setText("Forecasting " + location.getName() + ", " + location.getCountry());
        }
    }

    private void renderWeekForecastByLocation(Location location) {
        if (Objects.nonNull(weekForecastView)) {
            remove(weekForecastView);
        }
        updateLocationLabel(location);
        weatherForecastService.findWeatherForecast(location);
        weekForecastView = new WeekForecastView(this.weatherForecastService, wmoCodeMap);
        add(weekForecastView);
    }

    @NotNull
    private Location buildApiLocation(FavouriteLocation favouriteLocation) {
        Location apiLocation = new Location();
        apiLocation.setLatitude(favouriteLocation.getLatitude());
        apiLocation.setLongitude(favouriteLocation.getLongitude());
        apiLocation.setName(favouriteLocation.getName());
        apiLocation.setCountry(favouriteLocation.getCountry());
        return apiLocation;
    }

    private static Renderer<FavouriteLocation> renderCompositeProperty() {
        return LitRenderer.<FavouriteLocation>of("""
                <div>${item.name}, ${item.country}</div>
                """)
                .withProperty(Constants.FILED_NAME, FavouriteLocation::getName)
                .withProperty(Constants.FILED_COUNTRY, FavouriteLocation::getCountry);
    }

    private ComponentRenderer<Icon, FavouriteLocation> renderAction() {
        return new ComponentRenderer<>(favouriteLocation -> {
            final Icon close = new Icon(VaadinIcon.CLOSE_SMALL);
            close.getElement().addEventListener("click", event -> removeFavouriteLocation(favouriteLocation)).addEventData("event.stopPropagation()");
            return close;
        });
    }

    private void removeFavouriteLocation(FavouriteLocation location) {
        favouriteLocationService.removeFavouriteLocationById(location.getId());
        List<FavouriteLocation> list = favouriteLocationService.findAllFavouriteLocationsForLoggedInUser();
        if (list.isEmpty()) {
            favouriteLocationGrid.setItems(Collections.emptyList());
            updateLocationLabel(null);
            return;
        }
        favouriteLocationGrid.setItems(list);
        favouriteLocationGrid.select(list.get(0));
    }
}
