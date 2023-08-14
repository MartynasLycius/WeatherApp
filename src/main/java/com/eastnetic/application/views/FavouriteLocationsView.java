package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.locations.exceptions.FavouriteLocationException;
import com.eastnetic.application.locations.service.FavouriteLocationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@AnonymousAllowed
@Route(value = "favourite-locations", layout = MainLayout.class)
@PageTitle("Favourite Locations")
public class FavouriteLocationsView extends VerticalLayout implements BeforeEnterObserver {

    private static final Logger LOGGER = LogManager.getLogger(FavouriteLocationsView.class);

    private List<LocationDetails> favouriteLocations;

    private final LocationListComponent locationListComponent;

    private final FavouriteLocationService favouriteLocationService;

    public FavouriteLocationsView(LocationListComponent locationListComponent,
                                  FavouriteLocationService favouriteLocationService) {

        this.locationListComponent = locationListComponent;
        this.favouriteLocationService = favouriteLocationService;

        setSizeFull();

        add(locationListComponent);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        String username = (String) UI.getCurrent().getSession().getAttribute("username");

        List<LocationDetails> favouriteLocationList = getFavouriteLocations(username);

        if (favouriteLocationList == null || favouriteLocationList.isEmpty()) {
            Notification.show("No Favourite locations found.", 3000, Notification.Position.MIDDLE);
            return;
        }

        locationListComponent.showLocationList(favouriteLocationList);
    }

    private List<LocationDetails> getFavouriteLocations(String username) {

        try {

            return favouriteLocationService.getFavouriteLocations(username);

        } catch (FavouriteLocationException ex) {

            locationListComponent.setVisible(false);

            Notification.show("Error fetching Favourite locations. " + ex.getMessage(), 3000, Notification.Position.MIDDLE);

        } catch (Exception ex) {

            locationListComponent.setVisible(false);

            LOGGER.error("Error fetching Favourite locations: {}", ex.getMessage(), ex);

            Notification.show("Error fetching Favourite locations. Please try again later.", 3000, Notification.Position.MIDDLE);
        }

        return null;
    }
}
