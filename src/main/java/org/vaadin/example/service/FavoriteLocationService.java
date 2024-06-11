package org.vaadin.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vaadin.example.entity.Location;
import org.vaadin.example.entity.User;
import org.vaadin.example.repository.FavoriteLocationRepository;
import org.vaadin.example.repository.LocationRepository;

import java.util.List;

@ApplicationScoped
public class FavoriteLocationService {
    @Inject
    private FavoriteLocationRepository favoriteLocationRepository;

    @Inject
    private LocationRepository locationRepository;

    @Inject
    private SecurityService securityService;

    public void addFavoriteLocation(Location location) {
        // Fetch the authenticated user
        User authenticatedUser = securityService.getAuthenticatedUser();

        // Check if the location exists in the database
        Location existingLocation = locationRepository.findById(location.getId());

        if (existingLocation == null) {
            // Location doesn't exist, insert it into the database
            existingLocation = locationRepository.save(location);
        }

        favoriteLocationRepository.addFavoriteLocation(authenticatedUser, existingLocation);
    }

    public List<Location> getFavoriteLocations() {
        User authenticatedUser = securityService.getAuthenticatedUser();
        return favoriteLocationRepository.findFavoriteLocationsByUser(authenticatedUser);
    }
}