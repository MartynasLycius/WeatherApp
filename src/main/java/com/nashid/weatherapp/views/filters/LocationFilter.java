package com.nashid.weatherapp.views.filters;

import com.nashid.weatherapp.dto.Location;
import com.vaadin.flow.component.grid.dataview.GridListDataView;

public class LocationFilter {
    private final GridListDataView<Location> dataView;

    private String location;

    public LocationFilter(GridListDataView<Location> dataView) {
        this.dataView = dataView;
        this.dataView.addFilter(this::test);
    }

    public void setLocation(String location) {
        this.location = location;
        this.dataView.refreshAll();
    }

    public boolean test(Location location) {
        boolean matchesLocation = matches(location.getLocation(), this.location);
        return matchesLocation;
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty() || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
