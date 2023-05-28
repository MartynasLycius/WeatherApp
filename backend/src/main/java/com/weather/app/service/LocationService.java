package com.weather.app.service;

import com.weather.app.entity.FavouriteLocation;
import com.weather.app.model.FavouriteLocationRequestModel;
import com.weather.app.model.LocationModel;
import com.weather.app.model.LocationResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LocationService {
    ResponseEntity<LocationResponseModel> getLocation(String city);

    ResponseEntity<List<FavouriteLocation>> setFavoriteLocation(FavouriteLocationRequestModel favoriteLocation, Long userId);

    ResponseEntity<LocationModel> getLocationById(String locationId);

    ResponseEntity<FavouriteLocation> getFavouriteLocation(Long locationId);
}
