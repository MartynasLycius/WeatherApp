package com.eastnetic.application.locations.serviceImpl;

import com.eastnetic.application.locations.entity.FavouriteLocation;
import com.eastnetic.application.locations.entity.Location;
import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.locations.exceptions.FavouriteLocationException;
import com.eastnetic.application.locations.repository.FavouriteLocationRepository;
import com.eastnetic.application.locations.service.FavouriteLocationService;
import com.eastnetic.application.locations.service.LocationService;
import com.eastnetic.application.users.entity.User;
import com.eastnetic.application.users.exceptions.UserException;
import com.eastnetic.application.users.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteLocationServiceImpl implements FavouriteLocationService {

    private static final Logger LOGGER = LogManager.getLogger(FavouriteLocationServiceImpl.class);

    private final UserService userService;

    private final LocationService locationService;

    private final FavouriteLocationRepository favouriteLocationRepository;

    public FavouriteLocationServiceImpl(UserService userService,
                                        LocationService locationService,
                                        FavouriteLocationRepository favouriteLocationRepository) {
        this.userService = userService;
        this.locationService = locationService;
        this.favouriteLocationRepository = favouriteLocationRepository;
    }

    @Transactional
    @Override
    public void addFavouriteLocation(String username, LocationDetails locationDetails) {

        LOGGER.info("Add favourite location: username={}, Reference ID={}, Reference Source={}: START",
                username, locationDetails.getReferenceId(), locationDetails.getReferenceSource()
        );

        User user = userService.findUserByUsername(username);

        if (user == null) {
            throw new FavouriteLocationException("User not found.");
        }

        Location location = locationService.getLocationByReferenceIdAndSource(
                locationDetails.getReferenceId(), locationDetails.getReferenceSource()
        );

        if (location == null) {
            location = locationService.addLocation(locationDetails);
        }

        FavouriteLocation favouriteLocation = favouriteLocationRepository.findByUserAndLocation(user, location);

        if (favouriteLocation != null) {
            throw new FavouriteLocationException("This location already marked.");
        }

        favouriteLocation = new FavouriteLocation(user, location);

        try {

            favouriteLocationRepository.save(favouriteLocation);

            LOGGER.info("Add favourite location: username={}, Reference ID={}, Reference Source={}: SUCCESS",
                    username, locationDetails.getReferenceId(), locationDetails.getReferenceSource()
            );

        } catch (Exception e) {

            LOGGER.error("Add favourite location: username={}, Reference ID={}, Reference Source={}: FAILED",
                    username, locationDetails.getReferenceId(), locationDetails.getReferenceSource(), e
            );

            throw new FavouriteLocationException("Marked location error. Please try again later.");
        }
    }

    @Override
    public void deleteFavouriteLocation(String username, LocationDetails locationDetails) {

        LOGGER.info("Delete favourite location: username={}, Reference ID={}, Reference Source={}: START",
                username, locationDetails.getReferenceId(), locationDetails.getReferenceSource()
        );

        User user = userService.findUserByUsername(username);

        if (user == null) {
            throw new FavouriteLocationException("User not found.");
        }

        Location location = locationService.getLocationByReferenceIdAndSource(
                locationDetails.getReferenceId(), locationDetails.getReferenceSource()
        );

        FavouriteLocation favouriteLocation = favouriteLocationRepository.findByUserAndLocation(user, location);

        if (favouriteLocation == null) {
            throw new FavouriteLocationException("This location is not marked.");
        }

        try {

            favouriteLocationRepository.delete(favouriteLocation);

            deleteLocationIfNotInUsed(location);

            LOGGER.info("Delete favourite location: username={}, Reference ID={}, Reference Source={}: SUCCESS",
                    username, locationDetails.getReferenceId(), locationDetails.getReferenceSource()
            );

        } catch (Exception e) {

            LOGGER.error("Delete favourite location: username={}, Reference ID={}, Reference Source={}: FAILED",
                    username, locationDetails.getReferenceId(), locationDetails.getReferenceSource(), e
            );

            throw new FavouriteLocationException("Delete marked location error. Please try again later.");
        }
    }

    @Override
    public List<LocationDetails> getFavouriteLocations(String username) {
        return null;
    }

    @Override
    public boolean isFavouriteLocation(String username, LocationDetails locationDetails) {

        FavouriteLocation favouriteLocation = getFavouriteLocation(username, locationDetails);

        return favouriteLocation != null;
    }

    private FavouriteLocation getFavouriteLocation(String username, LocationDetails locationDetails) {

        User user = userService.findUserByUsername(username);

        if (user == null) {
            throw new UserException("User not found.");
        }

        Location location = locationService.getLocationByReferenceIdAndSource(
                locationDetails.getReferenceId(), locationDetails.getReferenceSource()
        );

        if (location == null) {
            return null;
        }

        return favouriteLocationRepository.findByUserAndLocation(user, location);
    }

    private void deleteLocationIfNotInUsed(Location location) {

        FavouriteLocation favouriteLocation = favouriteLocationRepository.findFirstByLocation(location);

        if (favouriteLocation == null) {

            locationService.deleteLocation(location);
        }
    }
}
