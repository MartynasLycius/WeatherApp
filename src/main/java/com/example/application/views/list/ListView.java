package com.example.application.views.list;

import com.example.application.data.*;
import com.example.application.services.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


import java.util.List;

@PageTitle("Weather App")
@Route(value = "")
public class ListView extends VerticalLayout implements FavoriteLocationUpdateListener
{
    Grid<Location> grid = new Grid<>(Location.class);
    TextField filterText = new TextField();
    LocationForm form;
    FavoriteLocations favoriteLocations;


    private final GeolocationApiService geolocationApiService;

    private TemperatureButtonClickListener buttonClickListener;

    private final CrmService service;

    public ListView(CrmService service, GeolocationApiService geolocationApiService)
    {
        this.geolocationApiService = geolocationApiService;
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();
        System.out.println("testing listView constructor");
        add(
                getToolbar(),
                getContent()
        );

        closeEditor();
    }

    private void closeEditor()
    {
        form.setVisible(false);
        removeClassName("editing");
    }

    private Component getContent()
    {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(1, grid);
        content.setFlexGrow(2, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }


    private void configureForm()
    {
        form = new LocationForm(service);
        form.setFavoriteLocationUpdateListener(this);
        form.setWidth("50em");
        form.setVisible(false);
    }

    private Component getToolbar()
    {
        filterText.setPlaceholder("Enter a location...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button searchButton = new Button("Search");
        searchButton.addClickListener(event ->
        {
            geolocationApiService.clearLocationList();
            if (!filterText.isEmpty()) // if search text is empty, dont fetch locations, just clear the list and update grid
            {
                geolocationApiService.fetchDataAndStoreInList(filterText.getValue()); // fetch and store data from API
            }
            List<Location> fetchedLocations = geolocationApiService.getLocationList();
            grid.setItems(fetchedLocations); // updating grid with fetched locations

        });
        searchButton.addClickShortcut(Key.ENTER);
        favoriteLocations = new FavoriteLocations(service);
        favoriteLocations.setSelectUpdateListener(form);
        HorizontalLayout toolbar = new HorizontalLayout(filterText, searchButton, favoriteLocations);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void configureGrid()
    {
        grid.addClassName("location-grid");
        grid.setSizeFull();
        grid.setColumns("locationName", "country", "timezone");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> selectForm(event.getValue()));

    }

    private void selectForm(Location location)
    {
        if (location == null)
        {
            closeEditor();
        } else
        {
            form.setHourlyViewVisible(false);
            form.updateForm(location);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    @Override
    public void onFavoriteLocationUpdated()
    {
        // update Select component with favorite locations from database
        favoriteLocations.updateSelectComponent();
    }


}

