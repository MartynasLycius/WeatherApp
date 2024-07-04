package com.nashid.weatherapp.views;

import com.nashid.weatherapp.domain.FavouriteLocation;
import com.nashid.weatherapp.dto.Location;
import com.nashid.weatherapp.services.FavouriteLocationService;
import com.nashid.weatherapp.services.LocationService;
import com.nashid.weatherapp.services.SettingsService;
import com.nashid.weatherapp.services.WeatherService;
import com.nashid.weatherapp.views.filters.LocationFilter;
import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Route("dashboard")
@CdiComponent
public class DashboardView extends Div implements BeforeEnterObserver {
    public static final Integer DEFAULT_LOCATION_PAGINATION_MAX = 10;

    private static Map<String, TextField> filterHeaders = new HashMap<>();

    @Inject
    public DashboardView(LocationService locationService, WeatherService weatherService, SettingsService settingsService, FavouriteLocationService favouriteLocationService) {
        locationService.setLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
        Span noDataLabel = new Span("Your text doesn't match with any Address, City or Zip Code");
        noDataLabel.addClassName("no-data-label");
        Span searchDataLabel = new Span("Please type a Address, City or Zip Code to get started");
        searchDataLabel.addClassName("no-data-label");
        noDataLabel.setVisible(false);
        //Top Layout - Part 1
        Span appName = new Span(LoginView.APPLICATION_NAME);
        appName.addClassName("logo");
        HorizontalLayout layout1 = new HorizontalLayout(appName);
        layout1.setAlignItems(FlexComponent.Alignment.START);
        layout1.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layout1.addClassName("app-logo");

        //Top Layout - Part 2
        TextField cityTextField = new TextField();
        cityTextField.addClassName("search-form-input");
        cityTextField.setPlaceholder("Search your Address, City or Zip Code");
        cityTextField.setId("search-form-input-id");
        cityTextField.getElement().setAttribute("style", "color: #FFFFFF;");
        Button searchButton = new Button();
        searchButton.addClassName("search-form-button");
        searchButton.setIcon(VaadinIcon.SEARCH.create());
        HorizontalLayout layout2 = new HorizontalLayout(cityTextField, searchButton);
        layout2.setAlignItems(FlexComponent.Alignment.CENTER);
        layout2.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        layout2.addClassName("search-form");

        //Top Layout - Part 3
        Button settingsButton = new Button();
        Icon settingIcon = VaadinIcon.COG_O.create();
        settingIcon.setColor("white");
        settingsButton.setIcon(settingIcon);
        settingsButton.addClickListener(buttonClickEvent -> {
           new SettingsView(settingsService);
        });
        Button logoutButton = new Button();
        Icon userIcon = VaadinIcon.SIGN_OUT.create();
        userIcon.setColor("white");
        logoutButton.setIcon(userIcon);
        logoutButton.addClickListener(buttonClickEvent -> {
            VaadinSession.getCurrent().setAttribute("LOGGED_IN_USER_ID", null);
            UI.getCurrent().getPage().setLocation("login");
        });
        HorizontalLayout layout3 = new HorizontalLayout(settingsButton, logoutButton);
        layout3.setAlignItems(FlexComponent.Alignment.END);
        layout3.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        //Top Layout
        HorizontalLayout topLayout = new HorizontalLayout(layout1, layout2, layout3);
        topLayout.addClassName("top-layout");
        add(topLayout);

        //Weather Dialog
        Dialog dialog = new Dialog();
        dialog.setWidth("1200px");
        dialog.setHeight("800px");

        Button closeButton = new Button("Close", event -> dialog.close());
        closeButton.addClassName("action-button");
        dialog.getFooter().add(closeButton);

        HorizontalLayout favouriteLayout = new HorizontalLayout();
        favouriteLayout.setVisible(false);
        favouriteLayout.addClassName("fav-layout");
        Span favlabel = new Span("Favourite Locations: ");
        favlabel.addClassName("fav-label");
        favouriteLayout.add(favlabel);
        favouriteLocationService.getCurrentUserFavouriteLocations().forEach( favLocation -> {
            String label = favLocation.getLocation();
            if (favLocation.getCountry() != null || favLocation.getCountry() != "") {
                label = label.concat(", ").concat(favLocation.getCountry());
            }
            Span favouriteLocation = new Span(label);
            favouriteLocation.addClassName("clickable-link-text");
            favouriteLocation.addClickListener(event -> {
                dialog.removeAll();
                WeatherUpdateView weatherUpdateView = new WeatherUpdateView(weatherService.getLocationFromFavouriteLocation(favLocation), weatherService, favouriteLocationService);
                dialog.setHeaderTitle("Weather Forecast for " + favLocation.getLabelText());
                dialog.add(weatherUpdateView);
                dialog.open();
            });
            favouriteLayout.add(favouriteLocation);
            favouriteLayout.setVisible(true);
        });
        add(favouriteLayout);

        Grid<Location> grid = new Grid<>(Location.class, false);
        Grid.Column<Location> locationColumn = grid.addColumn(new ComponentRenderer<>(location -> {
            Span locationLink = new Span(location.getLocation());
            locationLink.addClassName("clickable-link-text");
            Span countryCodeBadge = new Span(location.getCountryCode());
            countryCodeBadge.getElement().getThemeList().add("badge primary");
            locationLink.addClickListener(event -> {
                dialog.removeAll();
                WeatherUpdateView weatherUpdateView = new WeatherUpdateView(location, weatherService, favouriteLocationService);
                dialog.setHeaderTitle("Weather Forecast for " + location.getLabelText());
                dialog.add(weatherUpdateView);
                dialog.open();
            });
            HorizontalLayout horizontalLayout = new HorizontalLayout(locationLink, countryCodeBadge);
            return horizontalLayout;
        })).setWidth("250px").setFlexGrow(0).setHeader("Location");
        Grid.Column<Location> latitudeColumn = grid.addColumn(Location::getLatitude).setTextAlign(ColumnTextAlign.CENTER).setHeader("Latitude");
        Grid.Column<Location> longitudeColumn = grid.addColumn(Location::getLongitude).setTextAlign(ColumnTextAlign.CENTER).setHeader("Longitude");
        Grid.Column<Location> countryColumn = grid.addColumn(Location::getCountry).setTextAlign(ColumnTextAlign.CENTER).setHeader("Country");
        Grid.Column<Location> temperatureColumn = grid.addColumn(Location::getTemperature).setTextAlign(ColumnTextAlign.CENTER).setHeader("Temperature");
        Grid.Column<Location> surfaceWindColumn = grid.addColumn(Location::getSurfaceWind).setTextAlign(ColumnTextAlign.CENTER).setHeader("Surface Wind");
        Grid.Column<Location> rainColumn = grid.addColumn(Location::getRain).setTextAlign(ColumnTextAlign.CENTER).setHeader("Rain");
        Grid.Column<Location> lastUpdatedColumn = grid.addColumn(Location::getLastUpdatedTime).setTextAlign(ColumnTextAlign.CENTER).setHeader("Last Updated").setWidth("150px");
        Map<String, Grid.Column> components = new HashMap();
        components.put("locationColumn", locationColumn);
        components.put("latitudeColumn", latitudeColumn);
        components.put("longitudeColumn", longitudeColumn);
        components.put("countryColumn", countryColumn);
        components.put("temperatureColumn", temperatureColumn);
        components.put("surfaceWindColumn", surfaceWindColumn);
        components.put("rainColumn", rainColumn);
        components.put("lastUpdatedColumn", lastUpdatedColumn);
        grid.setClassNameGenerator(item -> "custom-grid-row");
        grid.setId("custom-grid");
        Button loadMoreButton = new Button("Load more ...");
        loadMoreButton.addClassName("load-more-button");
        HorizontalLayout horizontalLayout3 = new HorizontalLayout(loadMoreButton);
        layout3.setAlignItems(FlexComponent.Alignment.CENTER);
        layout3.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        grid.addClassName("custom-grid-header");
        HorizontalLayout horizontalLayout2 = new HorizontalLayout(grid);
        horizontalLayout2.addClassName("location-view");
        horizontalLayout3.addClassName("pagination-view");
        searchButton.addClickShortcut(Key.ENTER);
        add(horizontalLayout2);
        add(horizontalLayout3);

        HorizontalLayout horizontalLayout4 = new HorizontalLayout(searchDataLabel, noDataLabel);
        horizontalLayout4.addClassName("landing-view");
        add(horizontalLayout4);

        grid.setVisible(false);
        horizontalLayout3.setVisible(false);
        loadMoreButton.addClickListener(buttonClickEvent1 -> {
            loadMoreButton.setEnabled(false);
            locationService.addLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
            List<Location> locations = locationService.getLocationList(cityTextField.getValue(), locationService.getLocationCount());
            locations.forEach(location -> {
                location.setWeatherResult(weatherService.getCurrentWeather(location.getLatitude(), location.getLongitude()));
            });
            loadAndSetLocations(locations, grid, components, noDataLabel, searchDataLabel, locationService, horizontalLayout4, loadMoreButton);
            horizontalLayout3.setVisible(locationService.getLoadMore());
            loadMoreButton.setEnabled(true);
        });
        searchButton.addClickListener(buttonClickEvent -> {
            if (cityTextField.getValue() != null && cityTextField.getValue() != "") {
                locationService.setLocationCount(DEFAULT_LOCATION_PAGINATION_MAX);
                List<Location> locations = locationService.getLocationList(cityTextField.getValue(), locationService.getLocationCount());
                locations.forEach(location -> {
                    location.setWeatherResult(weatherService.getCurrentWeather(location.getLatitude(), location.getLongitude()));
                });
                loadAndSetLocations(locations, grid, components, noDataLabel, searchDataLabel, locationService, horizontalLayout4, loadMoreButton);
                horizontalLayout3.setVisible(locationService.getLoadMore());
            }
            else {
                grid.setVisible(false);
                noDataLabel.setVisible(false);
                searchDataLabel.setVisible(true);
                horizontalLayout3.setVisible(false);
                horizontalLayout4.setVisible(true);
            }
        });
    }

    private static void loadAndSetLocations(List<Location> locations, Grid<Location> grid, Map<String, Grid.Column> components, Span noDataLabel, Span searchDataLabel, LocationService locationService, HorizontalLayout horizontalLayout4, Button loadMoreButton) {
        GridListDataView<Location> dataView = grid.setItems(locations);
        LocationFilter locationFilter = new LocationFilter(dataView);

        if (grid.getHeaderRows().isEmpty()) {
            HeaderRow headerRow = grid.appendHeaderRow();
            headerRow.getCell(components.get("locationColumn")).setComponent(createFilterHeader("Location", locationFilter::setLocation));
            headerRow.getCell(components.get("countryColumn")).setText("Country");
            headerRow.getCell(components.get("latitudeColumn")).setText("Latitude");
            headerRow.getCell(components.get("longitudeColumn")).setText("Longitude");
            headerRow.getCell(components.get("countryColumn")).setText("Country");
            headerRow.getCell(components.get("temperatureColumn")).setText("Temperature");
            headerRow.getCell(components.get("surfaceWindColumn")).setText("Surface Wind");
            headerRow.getCell(components.get("rainColumn")).setText("Rain");
            headerRow.getCell(components.get("lastUpdatedColumn")).setText("Last Updated");
        }
        else {
            grid.getHeaderRows().forEach( headerRow -> {
                headerRow.getCell(components.get("locationColumn")).setComponent(createFilterHeader("Location", locationFilter::setLocation));
            });
        }

        if (locations != null && !locations.isEmpty()) {
            searchDataLabel.setVisible(false);
            noDataLabel.setVisible(false);
            horizontalLayout4.setVisible(false);
            loadMoreButton.setVisible(true);
            grid.setVisible(true);
        }
        else {
            searchDataLabel.setVisible(false);
            noDataLabel.setVisible(true);
            horizontalLayout4.setVisible(true);
            loadMoreButton.setVisible(false);
            grid.setVisible(false);
        }
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
        TextField textField;
        if (filterHeaders.get(labelText) != null) {
            textField = filterHeaders.get(labelText);
            textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));
        }
        else {
            textField = new TextField();
        }
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.setPlaceholder(labelText);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.getStyle().set("color", "white");
        textField.addValueChangeListener(e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID") == null) {
            beforeEnterEvent.forwardTo("login");
        }
    }
}
