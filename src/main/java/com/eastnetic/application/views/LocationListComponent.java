package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@UIScope
public class LocationListComponent extends VerticalLayout implements PaginationComponent.PaginationListener {

    private PaginationComponent paginationComponent;
    private List<LocationDetails> locationList = new ArrayList<>();
    private Grid<LocationDetails> locationGrid = new Grid<>(LocationDetails.class);

    public LocationListComponent() {

        locationGrid.setColumns("name", "country", "latitude", "longitude", "elevation", "timezone");
        locationGrid.setVisible(false);
        add(locationGrid);

        paginationComponent = new PaginationComponent(this);
        add(paginationComponent);
    }

    public void showLocationList(List<LocationDetails> locationList) {

        this.locationList = locationList;

        onPageChange(1);

        int totalPages = (int) Math.ceil((double) locationList.size() / paginationComponent.getPageSize());

        paginationComponent.setTotalPages(totalPages);
    }

    @Override
    public void onPageChange(int page) {

        if (locationList == null || locationList.isEmpty()) {
            locationGrid.setVisible(false);
            return;
        }

        int totalPages = (int) Math.ceil((double) locationList.size() / paginationComponent.getPageSize());
        int validPage = Math.min(Math.max(page, 1), totalPages);

        int fromIndex = (validPage - 1) * paginationComponent.getPageSize();
        int toIndex = Math.min(fromIndex + paginationComponent.getPageSize(), locationList.size());

        List<LocationDetails> pageLocations = locationList.subList(fromIndex, toIndex);

        locationGrid.setItems(pageLocations);
        locationGrid.setVisible(true);

        paginationComponent.setVisible(true);
        paginationComponent.setCurrentPage(page);
    }
}
