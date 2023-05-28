package com.weather.app.controller;

import com.weather.app.constants.Endpoints;
import com.weather.app.entity.FavouriteLocation;
import com.weather.app.model.FavouriteLocationRequestModel;
import com.weather.app.model.LocationModel;
import com.weather.app.model.LocationResponseModel;
import com.weather.app.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * LocationController for location api service
 *
 * @author raihan
 */
@RestController
@RequestMapping(Endpoints.API_LOCATION)
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    /**
     * getLocation is for getting locations
     *
     * @param city required param for getting locations
     * @return LocationResponseModel
     */
    @GetMapping(Endpoints.API_LOCATION_CITY_PATH_VARIABLE)
    public ResponseEntity<LocationResponseModel> getLocation(@PathVariable String city) {
        return locationService.getLocation(city);
    }

    /**
     * api for set favoriteLocation
     *
     * @param favoriteLocation type FavouriteLocationRequestModel
     * @return FavouriteLocation
     * @author raihan
     */
    @PostMapping(Endpoints.API_UPDATE_FAVOURITE_LOCATION)
    public ResponseEntity<List<FavouriteLocation>> setFavouriteLocation(@RequestBody FavouriteLocationRequestModel favoriteLocation,
                                                                        @PathVariable Long userId
    ) {
        return locationService.setFavoriteLocation(favoriteLocation,userId);
    }

    /**
     * api for getting favouriteLocation by id
     *
     * @param locationId type Long
     * @return FavouriteLocation
     * @author raihan
     */
    @GetMapping(Endpoints.API_GET_FAVOURITE_LOCATION)
    public ResponseEntity<FavouriteLocation> getFavouriteLocation(@PathVariable Long locationId) {
        return locationService.getFavouriteLocation(locationId);
    }

    /**
     * api for getting location by id
     *
     * @param locationId type String
     * @return LocationModel
     * @author raihan
     */
    @GetMapping(Endpoints.API_LOCATION_BY_ID)
    public ResponseEntity<LocationModel> getLocationById(@PathVariable String locationId) {
        return locationService.getLocationById(locationId);
    }
}
