package com.eastnetic.application.locations.service;

import com.eastnetic.application.locations.entity.LocationDetails;

import java.util.List;

public interface FavouriteLocationService {

    void addFavouriteLocation(String username, LocationDetails locationDetails);

    void deleteFavouriteLocation(String username, LocationDetails locationDetails);

    List<LocationDetails> getFavouriteLocations(String username);

    boolean isFavouriteLocation(String username, LocationDetails locationDetails);
}
