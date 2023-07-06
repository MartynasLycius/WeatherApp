package com.waheduzzaman.MeteoWeather.service.impl;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import com.waheduzzaman.MeteoWeather.data.entity.FavouriteLocation;
import com.waheduzzaman.MeteoWeather.data.entity.User;
import com.waheduzzaman.MeteoWeather.data.repository.FavouriteLocationRepository;
import com.waheduzzaman.MeteoWeather.security.UserDetailsServiceImpl;
import com.waheduzzaman.MeteoWeather.service.AbstractFavouriteLocationService;
import com.waheduzzaman.MeteoWeather.views.components.GEOComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteLocationServiceImpl extends AbstractFavouriteLocationService {
    @Value("${notification.message.null.record}")
    private String NULL_RECORD_NOTIFICATION_MSG;
    @Value("${notification.message.no.record}")
    private String NO_RECORD_NOTIFICATION_MSG;
    @Value("${notification.message.record.add}")
    private String ADD_SUCCESS_NOTIFICATION_MSG;
    @Value("${notification.message.record.delete}")
    private String DELETE_SUCCESS_NOTIFICATION_MSG;

    public FavouriteLocationServiceImpl(FavouriteLocationRepository favouriteLocationRepository, UserDetailsServiceImpl userDetailsService, GEOComponent geoComponent) {
        super(favouriteLocationRepository, userDetailsService, geoComponent);
    }

    @Override
    public void addNewFavouriteLocation(Location location) {
        if (location == null) {
            Notification.show(NULL_RECORD_NOTIFICATION_MSG).addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }
        FavouriteLocation favouriteLocation = new FavouriteLocation();
        favouriteLocation.setName(location.getName());
        favouriteLocation.setCountry(location.getCountry());
        favouriteLocation.setLongitude(location.getLongitude());
        favouriteLocation.setLatitude(location.getLatitude());
        favouriteLocation.setUser(userDetailsService.getLoggedInUser());
        favouriteLocationRepository.save(favouriteLocation);
        Notification.show(ADD_SUCCESS_NOTIFICATION_MSG).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    @Override
    public void removeFavouriteLocationByLocation(Location location) {
        if (!doesRecordExist(location))
            Notification.show(NO_RECORD_NOTIFICATION_MSG).addThemeVariants(NotificationVariant.LUMO_ERROR);

        FavouriteLocation favouriteLocation = findByLocation(location);
        favouriteLocationRepository.delete(favouriteLocation);
        Notification.show(DELETE_SUCCESS_NOTIFICATION_MSG).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    @Override
    public void removeFavouriteLocationById(Long id) {
        FavouriteLocation favouriteLocation = favouriteLocationRepository.findById(id).orElse(null);
        if (favouriteLocation == null) {
            Notification.show(NO_RECORD_NOTIFICATION_MSG).addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }
        favouriteLocationRepository.delete(favouriteLocation);
        Notification.show(DELETE_SUCCESS_NOTIFICATION_MSG).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    @Override
    public boolean doesRecordExist(Location location) {
        User contextUser = userDetailsService.getLoggedInUser();
        return (location != null) && favouriteLocationRepository.findByLatitudeAndLongitudeAndUser(location.getLatitude(), location.getLongitude(), contextUser) != null;
    }

    @Override
    public FavouriteLocation findByLocation(Location location) {
        User contextUser = userDetailsService.getLoggedInUser();
        return favouriteLocationRepository.findByLatitudeAndLongitudeAndUser(location.getLatitude(), location.getLongitude(), contextUser);
    }

    @Override
    public List<FavouriteLocation> findAllFavouriteLocationsForLoggedInUser() {
        User contextUser = userDetailsService.getLoggedInUser();
        List<FavouriteLocation> favouriteLocationList = favouriteLocationRepository.findAllByUser(contextUser);
        addCurrentLocationItem(contextUser, favouriteLocationList);
        return favouriteLocationList;
    }

    @Override
    public int countOfFavouriteLocations() {
        User contextUser = userDetailsService.getLoggedInUser();
        return favouriteLocationRepository.countAllByUser(contextUser);
    }

    @Override
    protected void addCurrentLocationItem(User contextUser, List<FavouriteLocation> favouriteLocationList) {
        if (geoComponent.isLocationAcquired()) {
            FavouriteLocation currentLocation = new FavouriteLocation();
            currentLocation.setUser(contextUser);
            currentLocation.setName("Current Location");
            currentLocation.setCountry("Current Location...");
            currentLocation.setLatitude(geoComponent.getLatitude());
            currentLocation.setLongitude(geoComponent.getLongitude());
            favouriteLocationList.add(0, currentLocation);
        }
    }
}