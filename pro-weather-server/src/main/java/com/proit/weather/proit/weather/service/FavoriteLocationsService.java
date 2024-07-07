package com.proit.weather.proit.weather.service;

import java.util.List;

import com.proit.weather.proit.weather.model.FavoriteLocation;

public interface FavoriteLocationsService {
	List<FavoriteLocation> getFavoriteAllLocations();
	FavoriteLocation getFavoriteLocationById(Long id);
	FavoriteLocation createFavoriteLocation(FavoriteLocation favoriteLocation);
	FavoriteLocation updateFavoriteLocation(Long id, FavoriteLocation favoriteLocation);
    void deleteFavoriteLocation(Long id);
}

