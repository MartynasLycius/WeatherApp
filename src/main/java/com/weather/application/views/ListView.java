package com.weather.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import com.weather.application.data.entity.Favourites;
import com.weather.application.data.service.FavouritesService;
import com.weather.application.data.service.WaService;
import com.weather.application.data.dto.DailyForecast;
import com.weather.application.data.dto.GeoCode;
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

import java.time.LocalDate;
import java.util.List;

@PageTitle("Contacts | WeatherApp")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class ListView extends VerticalLayout {
    Grid<GeoCode> grid = new Grid<>(GeoCode.class);
    TextField filterText = new TextField();
    ForecastView forecast;
    private WaService waService;
    private FavouritesService favouritesService;
    private Dialog favouriteDialog;

    public ListView(WaService waService, FavouritesService favouritesService) {
        this.waService = waService;
        this.favouritesService = favouritesService;
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
        forecast = new ForecastView(favouritesService);
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by city ...");
        filterText.setClearButtonVisible(true);
        filterText.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button getFavouritesButton = new Button("Favourites");
        getFavouritesButton.addClickListener(e -> getFavourites());

        HorizontalLayout toolBar = new HorizontalLayout(filterText, getFavouritesButton);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    private void getFavourites() {
        List<Favourites> favourites =  favouritesService.getFavouritesByUserIdAndFlag(1);
        openPopupFavourites(favourites);
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setWidthFull();
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
    private void viewForeCastByFavourite(Favourites favourites) {
        if(favourites == null){
            // Clear
        } else {
            GeoCode geoCode = new GeoCode();
            geoCode.setId(favourites.getGeoCodeId());
            geoCode.setLatitude(favourites.getLatitude());
            geoCode.setLongitude(favourites.getLongitude());
            geoCode.setName(favourites.getCityName());
            geoCode.setCountry(favourites.getCountryName());
            DailyForecast dailyForecast = waService.getDailyForecast(geoCode.getLatitude(), geoCode.getLongitude());
            forecast.setGeoCode(geoCode, dailyForecast, waService);
            forecast.setVisible(true);
            addClassName("editing");
            favouriteDialog.close();
        }
    }

    private void openPopupFavourites(List<Favourites> favourites) {
        favouriteDialog = new Dialog();

        VerticalLayout dialogLayout = new VerticalLayout();
        Span contentLabel = new Span("Number of favourites location: " + favourites.size() + " Current date: " + LocalDate.now());
        contentLabel.addClassNames("text-xl", "mt-m");
        contentLabel.setWidthFull();

        // start
        Grid<Favourites> favouritesGrid = new Grid<>(Favourites.class);
        favouritesGrid.addClassName("contact-grid");
        favouritesGrid.setWidthFull();
        favouritesGrid.setColumns("geoCodeId", "cityName", "countryName", "latitude", "longitude");
        favouritesGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        favouritesGrid.setPageSize(10);
        favouritesGrid.setItems(favourites);
        favouritesGrid.asSingleSelect().addValueChangeListener(e -> viewForeCastByFavourite(e.getValue()));
        // end

        Button closeButton = new Button("Close", e -> favouriteDialog.close());
        closeButton.getStyle().set("margin-left", "auto");

        dialogLayout.add(contentLabel, favouritesGrid, closeButton);
        dialogLayout.setWidthFull();
        favouriteDialog.add(dialogLayout);
        favouriteDialog.open();
    }

}
