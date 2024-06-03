package org.vaadin.example.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.vaadin.example.model.Location;
import org.vaadin.example.service.LocationService;
import org.vaadin.example.service.UserService;
import org.vaadin.example.util.Tools;

import java.util.List;
import java.util.logging.Logger;

public class FavoriteLocationsView extends VerticalLayout {

    private final UserService userService;
    private final LocationService locationService;

    private List<Location> favoriteLocations;
    private HorizontalLayout buttonLayout;

    private static final Logger LOGGER = Logger.getLogger(FavoriteLocationsView.class.getName());

    public FavoriteLocationsView(LocationService locationService,
                                 UserService userService) {
        this.userService = userService;
        this.locationService = locationService;
        initFavoriteLocationsView();
    }

    private void initFavoriteLocationsView() {
        if (userService.hasSession()) {
            loadFavoriteLocationsBySessionUser();
            buildFavoriteLocationsLayout();
        }
    }

    private void buildFavoriteLocationsLayout() {
        // Create the container for the favorite list
        Div favContainer = new Div();
        favContainer.setWidthFull();
        favContainer.addClassName("fav-container");

        // Create the spans
        Span spacer1 = new Span("Favorite List");
        Span spacer2 = new Span();
        spacer2.setWidth("70%");
        Span spacer3 = new Span(new Html("<i>Click on location to view weather forecast</i>"));
        spacer3.addClassName("text-align-right");

        // Create the horizontal layout for the legend
        HorizontalLayout favLegend = new HorizontalLayout();
        favLegend.setWidthFull();
        favLegend.add(spacer1, spacer2, spacer3);

        // Create the button layout
        buttonLayout = new HorizontalLayout();
        buttonLayout.getStyle().set("flex-wrap", "wrap");
        buttonLayout.setWidthFull();

        // Add buttons for each favorite location
        for (Location location : favoriteLocations) {
            Component button = generateFavListAsButton(location);
            button.addClassName("cursor-pointer");
            buttonLayout.add(button);
        }

        // Add the legend and button layout to the bordered container
        VerticalLayout containerLayout = new VerticalLayout(favLegend, buttonLayout);
        containerLayout.setPadding(false);
        containerLayout.setMargin(false);
        favContainer.add(containerLayout);

        // Add the container to the main layout
        add(favContainer);
    }

    private void loadFavoriteLocationsBySessionUser() {
        long userId = userService.getLoggedUserId();
        favoriteLocations = locationService.getUserFavoriteLocations(userId);
    }

    private Component generateFavListAsButton(Location location) {
        String locName = locationService.getLocationString(location);
        String emptySpc = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"; //added empty space to place delete button properly
        // Create the main button
        Button mainButton = new Button(new Html("<span>" + locName + emptySpc + "</span>"));
        mainButton.setClassName("cursor-pointer");
        mainButton.addClickListener(event -> {
            // If click, proceed with fetching weather forecast
            onClickFavLocation(location);
        });

        // Create the delete icon button
        Button deleteButton = new Button(VaadinIcon.CLOSE.create());
        deleteButton.addClickListener(event -> {
            showRemoveConfirmationDialog(locName, location.getId());
        });
        deleteButton.addClassNames("close-button", "cursor-pointer");

        // Create a layout to hold both the main button and the close icon
        HorizontalLayout buttonLayout = new HorizontalLayout(mainButton, deleteButton);
        buttonLayout.setAlignItems(Alignment.CENTER);

        return buttonLayout;
    }

    private void onClickFavLocation(Location location) {
        String locName = locationService.getLocationString(location);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        List<JsonObject> dailyData = locationService.getWeatherForecastApiData(Tools.WEATHER_FORECAST_TYPE_DAILY, latitude, longitude, "");
        locationService.displayDailyForecast(dailyData, latitude, longitude, locName);
    }

    // Method to create a confirmation dialog for removing favorite location
    private void showRemoveConfirmationDialog(String locName, long id) {
        Html message = new Html("<span>Are you sure you want to remove <b>" + locName + "</b> from favorites?</span>");

        Dialog confirmDialog = new Dialog();
        confirmDialog.setCloseOnEsc(false);
        confirmDialog.setCloseOnOutsideClick(false);

        // Confirmation message
        confirmDialog.add(message);

        // Buttons for confirmation
        Button confirmButton = new Button("Confirm", event -> {
            confirmDialog.close();
            locationService.deleteFavoriteLocation(id);
            // Recreate and populate the bordered container after removal
            updateFavoriteLocations();
        });
        Button cancelButton = new Button("Cancel", event -> confirmDialog.close());
        HorizontalLayout buttonsLayout = new HorizontalLayout(confirmButton, cancelButton);
        confirmDialog.add(buttonsLayout);

        confirmDialog.open();
    }

    public void updateFavoriteLocations() {
        loadFavoriteLocationsBySessionUser();
        // Remove all children from the buttonLayout
        buttonLayout.removeAll();

        // Add buttons for each favorite location
        for (Location location : favoriteLocations) {
            Component button = generateFavListAsButton(location);
            button.addClassName("cursor-pointer");
            buttonLayout.add(button);
        }
    }
}
