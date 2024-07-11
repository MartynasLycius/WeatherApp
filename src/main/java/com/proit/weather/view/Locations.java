package com.proit.weather.view;

import com.proit.weather.dto.LocationInfoDto;
import com.proit.weather.dto.LocationResult;
import com.proit.weather.model.LocationInfoModel;
import com.proit.weather.service.UserLocationService;
import com.proit.weather.service.WeatherInfoService;
import com.proit.weather.utils.Messages;
import com.proit.weather.view.litgrid.PaginatedGrid;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.proit.weather.utils.KEY_CONSTANTS.FAVOURITE_HEADER;
import static com.proit.weather.utils.KEY_CONSTANTS.LOCATION_SEARCH_TEXT;


@Route(value = "locations", layout = MainLayout.class)
public class Locations extends VerticalLayout {
    private static final String IS_FAVOURITE_KEY = "isFavorite";
    private final UserLocationService userLocationService = new UserLocationService();
    private final Grid<LocationInfoDto> grid = new PaginatedGrid<>(LocationInfoDto.class);
    private final TextField city = new TextField();

    public Locations() {
        add(city, grid);
        grid.setPageSize(10);
        grid.setItems(Collections.emptyList());
        city.setPlaceholder(Messages.get(LOCATION_SEARCH_TEXT));
        city.setClearButtonVisible(true);
        city.setValueChangeMode(ValueChangeMode.LAZY);
        city.addValueChangeListener(e -> onFilterChange());
        grid.addItemClickListener(item -> {
            LocationInfoDto locationInfoDto = item.getItem();
            UI.getCurrent().navigate(DailyForecast.class)
                .ifPresent(editor -> editor.showDailyWeather(locationInfoDto.getName(),
                        locationInfoDto.getLongitude(), locationInfoDto.getLatitude()));
        });
        grid.addComponentColumn(locationInfo -> {
            Checkbox checkbox = new Checkbox();
            checkbox.setValue(getFavouriteLocations().contains(locationInfo.getId()));
            checkbox.addValueChangeListener(event -> favouriteOnChange(locationInfo, event.getValue()));
            return checkbox;
        }).setHeader(Messages.get(FAVOURITE_HEADER)).setKey(IS_FAVOURITE_KEY);
    }

    private void onFilterChange() {
        if (StringUtils.isEmpty(city.getValue())) {
            return;
        }
        Optional<LocationResult> locations = WeatherInfoService.getLocations(city.getValue());
        if (locations.isPresent() && !locations.get().getResults().isEmpty()) {
            LocationResult locationResult = locations.get();
            grid.setItems(locationResult.getResults());
        }
    }

    private void favouriteOnChange(LocationInfoDto locationInfo, boolean isFavourite) {
        if (isFavourite) {
            userLocationService.saveFavoriteLocation(locationInfo);
        } else {
            userLocationService.removeFavourite(locationInfo.getId());
        }
    }

    private List<Long> getFavouriteLocations() {
        return userLocationService.getFavouriteLocations().stream().map(LocationInfoModel::getId).collect(Collectors.toList());
    }
}
