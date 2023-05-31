package com.proit.application.service;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.entity.Location;
import com.proit.application.data.entity.UserFavLocation;
import com.proit.application.data.repository.UserFavLocationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserFavLocationService {
    private final UserFavLocationRepository userFavLocationRepository;
    private final UserService userService;
    private final LocationService locationService;

    public Set<Long> getFavLocationsExternalIdsCurrentUser() {
        log.debug("getFavLocationsExternalIdsCurrentUser called");

        return getFavLocationsExternalIds(userService.getCurrentUserId());
    }

    public Set<Long> getFavLocationsExternalIds(long userid) {
        log.debug("getFavLocationsExternalIds called with userid={}", userid);

        List<Long> userFavLocations = userFavLocationRepository.findLocationIdsByUserId(userid);

        return locationService.findExternalIdsByLocationIds(userFavLocations);
    }

    public void removeFromFavoriteLocation(long externalId) {
        log.debug("removeFromFavoriteLocation called with externalId={}", externalId);

        removeFromFavoriteLocation(externalId, userService.getCurrentUserId());
    }

    public void removeFromFavoriteLocation(long externalId, long userId) {
        userFavLocationRepository.deleteByUserIdAndLocationId(userService.getCurrentUserId(), locationService.getLocationIdByExternalId(externalId));
    }


    public void addNewUserFavoriteLocation(LocationDto location) {
        log.debug("addNewUserFavoriteLocation called with location={}", location);

        addNewUserFavoriteLocation(location, userService.getCurrentUserId());
    }

    public void addNewUserFavoriteLocation(LocationDto location, long userId) {
        log.debug("addNewUserFavoriteLocation called with location={} for user={}", location, userId);

        Location existingLocation = locationService.findLocationByExternalId(location.getId());
        if (existingLocation == null) {
            log.info("Location with externalId={} not found, creating new one {}", location.getId(), location);

            existingLocation = locationService.saveLocation(location);
        }

        UserFavLocation userFavLocation = new UserFavLocation();
        userFavLocation.setLocationId(existingLocation.getId());
        userFavLocation.setUserId(userId);

        userFavLocationRepository.save(userFavLocation);
    }
}
