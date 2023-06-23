package com.hiddenhopes.WeatherApp.service;

import com.hiddenhopes.WeatherApp.model.FavoriteLocation;
import com.hiddenhopes.WeatherApp.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    void addFavoriteLocationByCoordinates(String username, FavoriteLocation favoriteLocation);

    void removeFavoriteLocationByCoordinates(String username, FavoriteLocation favoriteLocation);

    boolean isLocationFavorite(Long userId, double latitude, double longitude);

    List<FavoriteLocation> getFavoriteLocations(Long id);
}
