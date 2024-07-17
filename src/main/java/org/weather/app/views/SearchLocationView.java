package org.weather.app.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.weather.app.Utilities.VaadinUtils;
import org.weather.app.components.*;
import org.weather.app.entities.FavoriteCity;
import org.weather.app.entities.User;
import org.weather.app.models.Location;
import org.weather.app.services.FavoriteService;
import org.weather.app.services.LocationService;

import java.util.ArrayList;
import java.util.List;

/**
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route("search")
public class SearchLocationView extends AbstractView implements BeforeEnterObserver, SearchLocation{

    @Inject
    private LocationService locationService;
    @Inject
    private FavoriteService favoriteService;

    private SearchBar searchBarComponent;
    private LocationGrid locationGridComponent;
    private TextField locationFilterField;
    private VerticalLayout gridLayout;
    private HorizontalLayout paginationLayout;
    private List<FavoriteCity> favoriteCityList;
    private User loginUser;
    private Button previousButton;
    private Button nextButton;
    private Div pageInfo;
    private int currentPage = 1;
    private int totalPages = 1;
    private static final int PAGE_SIZE = 10;
    private List<Location> allLocations;


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        HttpServletRequest request = VaadinUtils.getCurrentHttpRequest();
        if (request != null && request.getAttribute("user") == null) {
            event.rerouteTo("login");
        }
        loginUser = (User) request.getAttribute("user");
        favoriteCityList = favoriteService.findAllFavoriteCityByUserId(loginUser.getId());
        searchBarComponent.setFavoriteCities(favoriteCityList);
    }

    @Override
    public void initializeComponents() {
        searchBarComponent = new SearchBar(this);
        searchBarComponent.getSearchButton().addClickListener(event -> handleSearchClick());

        locationFilterField = new TextFieldBuilder()
                .withPlaceholder("Filter by Location...")
                .withWidth("500px")
                .build();
        locationFilterField.addValueChangeListener(e -> applyFilter());
        locationGridComponent = new LocationGrid();
        locationGridComponent.addItemClickListener(event -> handleGridItemClick(event.getItem()));
        gridLayout = new VerticalLayoutBuilder().setPadding(true).build();
        gridLayout.setVisible(false);
        paginationLayout = new HorizontalLayoutBuilder().build();
        paginationLayout.setVisible(false);
        previousButton = new Button("Previous", e -> previousPage());
        nextButton = new Button("Next", e -> nextPage());
        nextButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previousButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        pageInfo = new Div();
        pageInfo.addClassNames("default-text", "padding-1");

    }

    @Override
    public void generateComponents() {
        gridLayout.add(locationFilterField, locationGridComponent);
        paginationLayout.add(previousButton, pageInfo, nextButton);
        add(searchBarComponent, gridLayout, paginationLayout);
    }

    private void handleSearchClick() {
        NotificationComponent.createNotification("Wait..Fetching data...", "success").open();
        fetchAndDisplayData(searchBarComponent.getSearchField().getValue());
    }

    private void handleGridItemClick(Location clickedLocation) {
        routeToNext(String.valueOf(clickedLocation.getTimeZone()), String.valueOf(clickedLocation.getCityName()), String.valueOf(clickedLocation.getLatitude()), String.valueOf(clickedLocation.getLongitude()));
    }

    private void fetchAndDisplayData(String cityName) {
        UI ui = UI.getCurrent();
        locationService.fetchLocations(cityName)
                .thenAccept(locations -> {
                    if (ui != null) {
                        ui.access(() -> {
                            gridLayout.setVisible(true);
                            paginationLayout.setVisible(true);
                            allLocations = locations;
                            totalPages = (int) Math.ceil((double) allLocations.size() / PAGE_SIZE);
                            currentPage = 1;
                            updatePageInfo();
                            updateGrid();
                        });
                    }
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    if (ui != null) {
                        ui.access(() -> Notification.show("Error fetching data: " + e.getMessage()));
                    }
                    return null;
                });
    }

    private void applyFilter() {
        locationGridComponent.applyFilter(locationFilterField.getValue());
    }

    @Override
    public void handleLogout() {
        removeCookies();
        getUI().ifPresent(ui -> ui.navigate("login"));
    }

    @Override
    public void onSelectFromFavorite(String cityName) {
        FavoriteCity favoriteCity = favoriteService.findCityByUserAndCityName(loginUser, cityName);
        if (favoriteCity != null) {
            routeToNext(favoriteCity.getCountry(), favoriteCity.getName(), favoriteCity.getLatitude(), favoriteCity.getLongitude());
        }
    }

    private void routeToNext(String country, String city, String latitude, String longitude){
        RouteParameters parameters = new RouteParameters(
                new RouteParam("country", country),
                new RouteParam("city", city),
                new RouteParam("latitude", latitude),
                new RouteParam("longitude", longitude)
        );
        UI.getCurrent().navigate(CurrentForecastView.class, parameters);
    }

    private void previousPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePageInfo();
            updateGrid();
        }
    }

    private void nextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updatePageInfo();
            updateGrid();
        }
    }

    private void updateGrid() {
        int fromIndex = (currentPage - 1) * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, allLocations.size());
        List<Location> currentPageItems = new ArrayList<>(allLocations.subList(fromIndex, toIndex));
        locationGridComponent.setData(currentPageItems);
    }

    private void updatePageInfo() {
        pageInfo.setText("Page " + currentPage + " of " + totalPages);
        previousButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);
    }


}

