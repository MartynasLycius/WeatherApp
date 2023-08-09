package com.example.application.views.list;

import com.example.application.data.service.WaService;
import com.example.application.dto.DailyForecast;
import com.example.application.dto.GeoCode;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
    Grid<GeoCode> grid = new Grid<>(GeoCode.class);
    TextField filterText = new TextField();
    Forecast forecast;
    private WaService waService;

    public ListView(WaService waService) {
        this.waService = waService;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForeCast();

        add(
           getToolbar(),
           getContent()
        );
        
        updateList();
    }

    private void updateList() {
        GeoCode[] result = waService.getGeoCodeResult(filterText.getValue());
        grid.setItems(result);
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, forecast);
        content.setFlexGrow(1, grid);
        content.setFlexGrow(1, forecast);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureForeCast() {
        forecast = new Forecast();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by city ...");
        filterText.setClearButtonVisible(true);
        filterText.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolBar = new HorizontalLayout(filterText);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setWidthFull();
//        grid.setSizeFull();
        grid.setColumns("name", "latitude", "longitude", "country", "id");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setPageSize(10);

        grid.asSingleSelect().addValueChangeListener(e -> viewForeCast(e.getValue()));
    }

    private void viewForeCast(GeoCode geoCode) {
        if(geoCode == null){
            // Clear
        } else {
            DailyForecast dailyForecast = waService.getDailyForecast(geoCode.getLatitude(), geoCode.getLongitude());
            forecast.setGeoCode(geoCode, dailyForecast, waService);
            forecast.setVisible(true);
            addClassName("editing");
        }
    }

}
