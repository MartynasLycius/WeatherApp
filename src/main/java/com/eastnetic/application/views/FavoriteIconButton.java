package com.eastnetic.application.views;

import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.locations.exceptions.FavouriteLocationException;
import com.eastnetic.application.locations.service.FavouriteLocationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FavoriteIconButton extends Button {

    private static final Logger LOGGER = LogManager.getLogger(FavoriteIconButton.class);

    private String username;

    private LocationDetails location;

    private boolean isFavorite = false;

    private final FavouriteLocationService favouriteLocationService;

    public FavoriteIconButton(FavouriteLocationService favouriteLocationService) {

        this.favouriteLocationService = favouriteLocationService;

        setIcon(VaadinIcon.STAR.create());
        addClickListener(event -> toggleFavorite());
    }

    public void setFavourite() {

        username = (String) UI.getCurrent().getSession().getAttribute("username");
        location = (LocationDetails) UI.getCurrent().getSession().getAttribute("selectedLocation");

        isFavorite = favouriteLocationService.isFavouriteLocation(username, location);

        updateFavoriteIconStyle();
    }

    private void toggleFavorite() {

        if (isFavorite()) {
            deleteFavorite();
        } else {
            markFavorite();
        }

        isFavorite = !isFavorite;

        updateFavoriteIconStyle();
    }

    private boolean isFavorite() {
        return isFavorite;
    }

    private void markFavorite() {

        try {

            favouriteLocationService.addFavouriteLocation(username, location);

        } catch (FavouriteLocationException ex) {

            LOGGER.error("Error marking location: {}", ex.getMessage(), ex);

            Notification.show("Error marking locations. " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER);

        } catch (Exception ex) {

            LOGGER.error("Error marking locations: {}", ex.getMessage(), ex);

            Notification.show("Error marking locations. Please try again later.", 3000, Notification.Position.TOP_CENTER);
        }
    }

    private void deleteFavorite() {

        try {

            favouriteLocationService.deleteFavouriteLocation(username, location);

        } catch (FavouriteLocationException ex) {

            LOGGER.error("Error removing marked locations: {}", ex.getMessage(), ex);

            Notification.show("Error removing marked locations. " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER);

        } catch (Exception ex) {

            LOGGER.error("Error removing marked locations: {}", ex.getMessage(), ex);

            Notification.show("Error removing marked locations. Please try again later.", 3000, Notification.Position.TOP_CENTER);
        }
    }

    private void updateFavoriteIconStyle() {

        boolean isFavorite = isFavorite();

        getStyle().set("padding", "5px");
        getStyle().set("border", "2px solid black");
        getStyle().set("color", isFavorite ? "orange" : "black");
    }
}