package com.eastnetic.application.locations.serviceImpl;

import com.eastnetic.application.locations.entity.Location;
import com.eastnetic.application.locations.entity.LocationDetails;
import com.eastnetic.application.locations.repository.LocationRepository;
import com.eastnetic.application.locations.service.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LogManager.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getLocationById(long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public void addLocation(LocationDetails locationDetails) {

        Location location = getLocationByReferenceIdAndSource(
                locationDetails.getReferenceId(),
                locationDetails.getReferenceSource()
        );

        if (location != null) {
            return;
        }

        location = convertDtoToEntity(locationDetails);

        try {

            locationRepository.save(location);

        } catch (Exception e) {

            LOGGER.error("Saving location failed. Reference ID={}, Reference Source={}",
                    locationDetails.getReferenceId(), locationDetails.getReferenceSource(), e
            );

            throw e;
        }
    }

    @Override
    public void deleteLocation(LocationDetails locationDetails) {

        Location location = getLocationByReferenceIdAndSource(
                locationDetails.getReferenceId(),
                locationDetails.getReferenceSource()
        );

        if (location == null) {
            return;
        }

        try {

            locationRepository.delete(location);

        } catch (Exception e) {

            LOGGER.error("Delete location failed. Reference ID={}, Reference Source={}",
                    locationDetails.getReferenceId(), locationDetails.getReferenceSource(), e
            );
        }
    }

    @Override
    public Location getLocationByReferenceIdAndSource(String id, String source) {
        return locationRepository.findByReferenceIdAndReferenceSource(id, source);
    }

    private Location convertDtoToEntity(LocationDetails locationDetails) {

        return new Location(
                locationDetails.getReferenceId(),
                locationDetails.getName(),
                locationDetails.getName(),
                locationDetails.getLatitude(),
                locationDetails.getLongitude(),
                locationDetails.getElevation(),
                locationDetails.getCountryCode(),
                locationDetails.getTimezone(),
                locationDetails.getCountry(),
                locationDetails.getAdmin1()
        );
    }
}
