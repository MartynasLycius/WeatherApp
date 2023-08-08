package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@UIScope
public class LocationListComponent extends VerticalLayout {

    private Grid<LocationDetails> locationGrid = new Grid<>(LocationDetails.class);

    public LocationListComponent() {

        locationGrid.setColumns("name", "country", "latitude", "longitude", "elevation", "timezone");
        locationGrid.setVisible(false);
        locationGrid.setHeight("27em");
        add(locationGrid);
    }

    public void showLocationList(List<LocationDetails> locationList) {

        if (locationList != null && !locationList.isEmpty()) {
            locationGrid.setVisible(true);
            locationGrid.setItems(locationList);
        }
    }
}
