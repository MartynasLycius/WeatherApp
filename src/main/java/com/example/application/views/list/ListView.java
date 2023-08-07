package com.example.application.views.list;

import com.example.application.data.entity.GeoLocation;
import com.example.application.data.service.WaService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("Contacts | WeatherApp")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout {
    Grid<GeoLocation> grid = new Grid<>(GeoLocation.class);
    TextField filterText = new TextField();
    Forecast forecast;
    private WaService waService;

    public ListView(WaService waService) {
        this.waService = waService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
           getToolbar(),
           getContent()
        );
        
//        updateList();
    }

//    private void updateList() {
//        grid.setItems(waService.findAllContacts(filterText.getValue()));
//    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, forecast);
        content.setFlexGrow(1, grid);
        content.setFlexGrow(1, forecast);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        forecast = new Forecast();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by city ...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
//        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolBar = new HorizontalLayout(filterText);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name", "latitude", "longitude", "country", "id");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(e -> editContact(e.getValue()));
    }

    private void editContact(Object forecast) {

    }

}
