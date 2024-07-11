package com.proit.weather.view;

import com.proit.weather.model.LocationInfoModel;
import com.proit.weather.service.UserLocationService;
import com.proit.weather.utils.Messages;
import com.proit.weather.view.litgrid.PaginatedGrid;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

import static com.proit.weather.utils.KEY_CONSTANTS.FAVOURITE_HEADER;


@Route(value = "favourites", layout = MainLayout.class)
public class Favourites extends VerticalLayout {
    private static final String IS_FAVOURITE_KEY = "isFavorite";
    private final UserLocationService locationInfoService = new UserLocationService();
    private final Grid<LocationInfoModel> grid = new PaginatedGrid<>(LocationInfoModel.class);
    List<LocationInfoModel> favouriteLocations;

    public Favourites() {
        favouriteLocations = locationInfoService.getFavouriteLocations();
        grid.setPageSize(10);
        grid.setItems(locationInfoService.getFavouriteLocations());
        grid.addComponentColumn(locationInfo -> {
            Checkbox checkbox = new Checkbox();
            checkbox.setValue(true);
            checkbox.addValueChangeListener(event -> favouriteOnChange(locationInfo));
            return checkbox;
        }).setHeader(Messages.get(FAVOURITE_HEADER)).setKey(IS_FAVOURITE_KEY);
        grid.addItemClickListener(item -> {
            LocationInfoModel locationInfoModel = item.getItem();
            UI.getCurrent().navigate(DailyForecast.class)
                    .ifPresent(editor -> editor.showDailyWeather(locationInfoModel.getName(),
                            locationInfoModel.getLongitude(), locationInfoModel.getLatitude()));
        });
        add(grid);
    }

    private void favouriteOnChange(LocationInfoModel locationInfo) {
        locationInfoService.removeFavourite(locationInfo.getId());
        favouriteLocations.remove(locationInfo);
        grid.setItems(favouriteLocations);
    }
}
