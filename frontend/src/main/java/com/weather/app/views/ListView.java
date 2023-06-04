package com.weather.app.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.weather.app.model.DailyWeatherResponseModel;
import com.weather.app.model.LocationModel;
import com.weather.app.model.LocationResponseModel;
import com.weather.app.service.LocationService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@PageTitle("Locations")
@Route(value = "location", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {
    Grid<LocationModel> grid = new Grid<>(LocationModel.class);
    Grid<DailyWeatherResponseModel> weatherGridDaily = new Grid<>(DailyWeatherResponseModel.class);
    TextField filterText = new TextField();
    private final LocationService locationService;

    public ListView(LocationService locationService) {
        this.locationService = locationService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(), grid);
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name", "latitude", "longitude", "timezone", "country");
        grid.addColumn(createActionRenderer()).setHeader("Actions").setFrozenToEnd(true)
                .setAutoWidth(true).setFlexGrow(0);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureWeatherGridDaily() {
        weatherGridDaily.addClassName("weather-grid");
        weatherGridDaily.setSizeFull();
        weatherGridDaily.setColumns("latitude", "longitude", "timezone");
    }

    private static Renderer<LocationModel> createActionRenderer() {
        return LitRenderer.<LocationModel>of(
                        "<vaadin-button @click=\"${checkWeather}\">Check Weather</vaadin-button>")
                .withFunction("checkWeather", locationModel -> {
                            UI.getCurrent().getPage().setLocation("daily?timezone=" + locationModel.getTimezone() + "&latitude=" + locationModel.getLatitude()
                                    + "&longitude=" + locationModel.getLatitude());
                        }
                )
                ;

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by city...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button searchButton = new Button("Search", click -> {
            LocationResponseModel locationResponseModel = locationService.getLocation(filterText.getValue());
            if (!(locationResponseModel == null || locationResponseModel.getResults().isEmpty())) {
                grid.setItems(locationResponseModel.getResults());
            }
        });
        var toolbar = new HorizontalLayout(filterText, searchButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
}