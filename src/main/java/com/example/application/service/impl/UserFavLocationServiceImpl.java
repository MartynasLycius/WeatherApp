package com.example.application.service.impl;

import com.example.application.data.dto.LocationDto;
import com.example.application.data.entity.Location;
import com.example.application.data.entity.UserFavLocation;
import com.example.application.data.repository.UserFavLocationRepository;
import com.example.application.service.LocationService;
import com.example.application.service.UserFavLocationService;
import com.example.application.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserFavLocationServiceImpl implements UserFavLocationService {
    private final UserFavLocationRepository userFavLocationRepository;
    private final UserService userService;
    private final LocationService locationService;

    public Set<Long> getFavLocationsLocationIdsCurrentUser() {
        log.debug("getFavLocationsLocationIdsCurrentUser called");
        List<Long> userFavLocations = userFavLocationRepository.findLocationIdsByUserId(userService.getCurrentUserId());
        return locationService.findIdsByLocationIds(userFavLocations);
    }

    public void removeFromFavoriteLocation(Long locationId) {
        log.debug("removeFromFavoriteLocation called with locationId={}", locationId);
        userFavLocationRepository.deleteByUserIdAndLocationId(userService.getCurrentUserId(), locationService.getIdByLocationId(locationId));
    }

    public Page<LocationDto> getAllFavoriteLocationOfCurrentUser(Pageable pageable, String locationNameFilter) {

        Long currentUserId = userService.getCurrentUserId();

        var locationIds = userFavLocationRepository.findLocationIdsByUserId(currentUserId);

        Page<Location> locations = locationService.findAllByLocationIdIn(locationIds, pageable);
        return locations.map(locationService::mapLocationToLocationDto);
    }



    public void addNewUserFavoriteLocation(LocationDto location) {
        Location existingLocation = locationService.findLocationByLocationId(location.getId());
        if (existingLocation == null) {
            existingLocation = locationService.saveLocation(location);
        }

        UserFavLocation userFavLocation = new UserFavLocation();
        userFavLocation.setLocationId(existingLocation.getId());
        userFavLocation.setUserId(userService.getCurrentUserId());

        userFavLocationRepository.save(userFavLocation);
    }

}
