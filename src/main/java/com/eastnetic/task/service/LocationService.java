package com.eastnetic.task.service;

import com.eastnetic.task.model.dao.UserFavLocations;
import com.eastnetic.task.model.dto.LocationDTO;
import com.eastnetic.task.model.dto.LocationResults;

import java.util.List;

public interface LocationService {

    LocationDTO getLocations(String cityName);
    void saveFavorites(LocationResults locationResults);
    List<UserFavLocations> getFavorites(int userId);
    boolean isSavedLocation(LocationResults locationResults, int userId);
    boolean deleteFavorites(UserFavLocations userFavLocations);
    UserFavLocations getFavoritesById(String id);
}
