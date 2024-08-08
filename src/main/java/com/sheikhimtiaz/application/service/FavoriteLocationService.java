package com.sheikhimtiaz.application.service;


import com.sheikhimtiaz.application.repository.FavoriteLocationRepository;
import com.sheikhimtiaz.application.entity.FavoriteLocation;
import com.sheikhimtiaz.application.entity.Location;
import com.sheikhimtiaz.application.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteLocationService {
    @Autowired private FavoriteLocationRepository favoriteRepository;
    @Autowired private LocationRepository locationRepository;

    public List<Location> getFavorites(String username) {
        List<Long> list = favoriteRepository.findByUsername(username).stream()
                .map(FavoriteLocation::getLocationId)
                .toList();
        List<Location> result = new ArrayList<>();
        for(Long locationId: list){
            Optional<Location> location = locationRepository.findById(locationId);
            location.ifPresent(result::add);
        }
        return result;
    }

    public void addFavorite(String username, Location location) {
        FavoriteLocation favorite = new FavoriteLocation();
        favorite.setUsername(username);
        favorite.setLocationId(location.getId());
        favoriteRepository.save(favorite);
    }

    public void removeFavorite(String username, Location location) {
        List<FavoriteLocation> favorites = favoriteRepository.findByUsername(username);
        for (FavoriteLocation favorite : favorites) {
            if (favorite.getLocationId().equals(location.getId())) {
                favoriteRepository.deleteById(favorite.getLocationId());
                break;
            }
        }
    }
}
