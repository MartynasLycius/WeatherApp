package org.weather.app.components;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.weather.app.models.Location;

import java.util.List;

public class LocationGrid extends Grid<Location> {

    private ListDataProvider<Location> dataProvider;

    public LocationGrid() {
        addClassName("styling");
        addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        addColumn(Location::getCityName).setHeader("City Name").setClassNameGenerator(item -> "custom-grid");;
        addColumn(Location::getTimeZone).setHeader("Location").setClassNameGenerator(item -> "custom-grid");;
        addColumn(Location::getLatitude).setHeader("Latitude").setClassNameGenerator(item -> "custom-grid");;
        addColumn(Location::getLongitude).setHeader("Longitude").setClassNameGenerator(item -> "custom-grid");;


        addThemeVariants(GridVariant.LUMO_NO_BORDER);
    }

    public void setData(List<Location> locations) {
        if (dataProvider == null) {
            dataProvider = new ListDataProvider<>(locations);
            setDataProvider(dataProvider);
        } else {
            dataProvider.getItems().clear();
            dataProvider.getItems().addAll(locations);
            dataProvider.refreshAll();
        }
    }

    public void applyFilter(String filterText) {
        if (dataProvider != null) {
            dataProvider.setFilter(location -> {
                if (filterText.trim().isEmpty()) {
                    return true;
                }
                return location.getTimeZone().toLowerCase().contains(filterText.toLowerCase());
            });
        }
    }
}