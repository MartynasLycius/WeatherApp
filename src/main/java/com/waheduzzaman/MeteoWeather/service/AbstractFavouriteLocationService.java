package com.waheduzzaman.MeteoWeather.service;

import com.waheduzzaman.MeteoWeather.data.dto.location.Location;
import com.waheduzzaman.MeteoWeather.data.entity.FavouriteLocation;
import com.waheduzzaman.MeteoWeather.data.entity.User;
import com.waheduzzaman.MeteoWeather.data.repository.FavouriteLocationRepository;
import com.waheduzzaman.MeteoWeather.security.UserDetailsServiceImpl;
import com.waheduzzaman.MeteoWeather.views.components.GEOComponent;

import java.util.List;

public abstract class AbstractFavouriteLocationService {
    protected final FavouriteLocationRepository favouriteLocationRepository;
    protected final UserDetailsServiceImpl userDetailsService;
    protected final GEOComponent geoComponent;

    protected AbstractFavouriteLocationService(FavouriteLocationRepository favouriteLocationRepository, UserDetailsServiceImpl userDetailsService, GEOComponent geoComponent) {
        this.favouriteLocationRepository = favouriteLocationRepository;
        this.userDetailsService = userDetailsService;
        this.geoComponent = geoComponent;
    }

    public abstract void addNewFavouriteLocation(Location location);

    public abstract void removeFavouriteLocationByLocation(Location location);

    public abstract void removeFavouriteLocationById(Long id);

    public abstract boolean doesRecordExist(Location location);

    public abstract FavouriteLocation findByLocation(Location location);

    public abstract List<FavouriteLocation> findAllFavouriteLocationsForLoggedInUser();

    public abstract int countOfFavouriteLocations();

    protected abstract void addCurrentLocationItem(User contextUser, List<FavouriteLocation> favouriteLocationList);
}
