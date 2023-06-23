package com.hiddenhopes.WeatherApp.ui;

import com.hiddenhopes.WeatherApp.model.Location;
import com.hiddenhopes.WeatherApp.service.LocationService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("")
public class GeocodingUI extends VerticalLayout {

    private LocationService geocodingService;

    private Grid<Location> grid;
    private TextField searchField;

    @Autowired
    public GeocodingUI(LocationService geocodingService) {
        this.geocodingService = geocodingService;

        searchField = new TextField("Search");
        searchField.setPlaceholder("Enter location name");
        searchField.setValueChangeMode(ValueChangeMode.LAZY);
        searchField.addValueChangeListener(event -> searchLocation(event.getValue()));

        grid = new Grid<>(Location.class);
        grid.setColumns("name", "latitude", "longitude");
        grid.setSizeFull();

        add(searchField, grid);
        setWidth("100%");
        setHeight("100%");

        refreshGrid();
    }

    private void searchLocation(String name) {
        refreshGrid(name);
    }

    private void refreshGrid() {
        refreshGrid(null);
    }

    private void refreshGrid(String name) {
        List<Location> results;
        if (name != null && !name.isEmpty()) {
            results = geocodingService.searchLocations(name);
        } else {
            results = geocodingService.searchLocations("your_location_name");
        }
        grid.setItems(results);
    }

}