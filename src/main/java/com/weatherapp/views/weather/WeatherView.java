package com.weatherapp.views.weather;

import com.vaadin.flow.component.UI;
import com.weatherapp.model.User;
import com.weatherapp.model.UserFavourites;
import com.weatherapp.detail.LocationInformation;
import com.weatherapp.detail.LocationResponse;
import com.weatherapp.security.SecurityService;
import com.weatherapp.services.UserFavouritesSerivce;

import com.weatherapp.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;

import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;
import com.weatherapp.pagination.PaginatedGrid;


import java.util.ArrayList;
import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created on 09 Jun, 2024
 */
@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Weather App")

public class WeatherView extends VerticalLayout {

    private final HttpServletRequest request;

    private final UserFavouritesSerivce userFavouritesSerivce;
    private List<UserFavourites> favouriteLists = new ArrayList<>();
    private PaginatedGrid<LocationInformation, LocationInformation> grid = new PaginatedGrid<>();
    private TextField filterText = new TextField();
    private ComboBox<UserFavourites> favouriteListDropDown = new ComboBox<>("My Favourites (Country-City)");
    private NativeLabel locationsByCityNameLabel = new NativeLabel("Locations by city name ");
    private WeatherFormDetails form;

    private final WebClient client;

    private final SecurityService securityService;
    private final String baseURLForLocationSearch="https://geocoding-api.open-meteo.com/v1/search";
    public WeatherView(WebClient.Builder builder, HttpServletRequest request, UserFavouritesSerivce userFavouritesSerivce, SecurityService securityService) {
        this.userFavouritesSerivce = userFavouritesSerivce;
        this.request = request;
        this.client = builder.baseUrl(baseURLForLocationSearch).build();
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), locationsByCityNameLabel, getWeather());
        this.securityService = securityService;

        User user = (User) request.getSession().getAttribute("User");
        if (user == null) {
            securityService.logout();
            UI.getCurrent().navigate("login");
            return;
        }

    }

    private Component getWeather() {
        VerticalLayout content = new VerticalLayout(grid, form);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new WeatherFormDetails(WebClient.builder());
    }


    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Type city name to search...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        loadFavouriteList();

        var toolbar = new HorizontalLayout(filterText, favouriteListDropDown);
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.addClassName("toolbar");

        toolbar.setFlexGrow(1, filterText);
        toolbar.setFlexGrow(2, favouriteListDropDown);
        toolbar.setWidthFull();
        return toolbar;
    }

    private void updateList() {

        LocationResponse locationResponse = client.get().uri(uri -> uri.path("/")
                        .queryParam("name", filterText.getValue())
                        .queryParam("count", 100)
                        .queryParam("language", "en")
                        .queryParam("format", "json")
                        .build())
                .retrieve()
                .toEntity(LocationResponse.class)
                .block()
                .getBody();

        locationResponse.getResults().forEach(locationInformation -> {
            User user = (User) request.getSession().getAttribute("User");
            if (user == null) {
                return;
            }
            locationInformation.setFavorite(favouriteLists.stream().anyMatch(favourite ->
                    favourite.getLatitude() == locationInformation.getLatitude()
                            && favourite.getLongitude() == locationInformation.getLongitude()
            ));
        });

        grid.setItems(locationResponse.getResults());
        grid.setPageSize(10);



    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");


        grid.addColumn(LocationInformation::getCountry).setHeader("Country").setSortable(true);
        grid.addColumn(LocationInformation::getName).setHeader("City Name").setSortable(true);
        grid.addColumn(LocationInformation::getLatitude).setHeader("Latitude").setSortable(true);
        grid.addColumn(LocationInformation::getLongitude).setHeader("Longitude").setSortable(true);



        grid.addComponentColumn(person -> {
            Checkbox checkbox = new Checkbox();
            checkbox.setValue(person.isFavorite());
            checkbox.addValueChangeListener(event -> {

                favouriteOnChange(person, event.getValue());
            });
            return checkbox;
        }).setHeader("Favorite").setKey("isFavorite");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setPageSize(10);

        grid.asSingleSelect().addValueChangeListener(event ->
                showDetailsWeather(event.getValue()));

    }


    private void showDetailsWeather(LocationInformation locationInformation) {
        if (locationInformation == null) {
            closeView();
        } else {
            form.setWeatherDetails(locationInformation);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeView() {
        form.setWeatherDetails(null);
        form.setVisible(false);
        removeClassName("editing");
    }


    private void favouriteOnChange(LocationInformation locationInformation, boolean isFavourite) {
        if (isFavourite == true) {
            addFavourite(locationInformation);
        }
        if (isFavourite == false) {
            removeFavourite(locationInformation);
        }
    }

    private void addFavourite(LocationInformation locationInformation) {
        User user = (User) request.getSession().getAttribute("User");
        if (user == null) {
            UI.getCurrent().navigate("login");
            return;
        }
        userFavouritesSerivce.addFavourite(user.getId(), locationInformation);
        loadFavouriteList();
    }

    private void removeFavourite(LocationInformation locationInformation) {
        User user = (User) request.getSession().getAttribute("User");
        if (user == null) {
            UI.getCurrent().navigate("login");
            return;
        }
        userFavouritesSerivce.removeFavourite(locationInformation, user.getId());
        loadFavouriteList();
    }

    private void loadFavouriteList() {
        User user = (User) request.getSession().getAttribute("User");
        if (user == null) {
            UI.getCurrent().navigate("login");
            return;
        }
        favouriteLists = userFavouritesSerivce.getFavourites(user.getId());


        favouriteListDropDown.setItems(favouriteLists);
        favouriteListDropDown.setItemLabelGenerator(UserFavourites::getCountryCity);
        favouriteListDropDown.addValueChangeListener(e -> {
            filterText.setValue(e.getValue().getCity());
            updateList();
        });
    }
}
