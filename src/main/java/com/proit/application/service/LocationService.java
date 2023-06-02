package com.proit.application.service;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.entity.Location;
import com.proit.application.data.repository.LocationRepository;
import com.proit.application.data.repository.UserFavLocationRepository;
import com.proit.application.data.specifications.LocationSpecifications;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class LocationService {
    private final UserService userService;
    private final LocationRepository locationRepository;
    private final UserFavLocationRepository userFavLocationRepository;

    public Page<LocationDto> getAllFavoriteLocationOfCurrentUser(Pageable pageable, String locationNameFilter) {
        log.debug("getAllFavoriteLocationOfCurrentUser: pageable={}, locationNameFilter={}", pageable, locationNameFilter);

        return this.getAllFavoriteLocationOfUser(pageable, locationNameFilter, userService.getCurrentUserId());
    }

    public Page<LocationDto> getAllFavoriteLocationOfUser(Pageable pageable, String locationNameFilter, Long userId) {
        log.info("getAllFavoriteLocationOfCurrentUser: pageable={}, locationNameFilter={}, userId={}", pageable, locationNameFilter, userId);

        var locationIds = userFavLocationRepository.findLocationIdsByUserId(userId);

        Specification<Location> specification = Specification.where(LocationSpecifications.withIdIn(locationIds));

        if (locationNameFilter != null && !locationNameFilter.isEmpty()) {
            specification = specification.and(LocationSpecifications.withName(locationNameFilter));
        }

        Page<Location> locations = locationRepository.findAll(specification, pageable);
        return locations.map(this::fromLocation);
    }

    public Long getLocationIdByExternalId(Long externalId) {
        log.debug("getLocationIdByExternalId: externalId={}", externalId);

        return locationRepository.findIdByExternalId(externalId);
    }

    public Set<Long> findExternalIdsByLocationIds(List<Long> locationIds) {
        log.debug("findExternalIdsByLocationIds: locationIds={}", locationIds);

        return locationRepository.findExternalIdsByIdIn(locationIds);
    }

    public Location findLocationByExternalId(Long externalId) {
        log.debug("findLocationByExternalId: externalId={}", externalId);

        return locationRepository.findByExternalId(externalId);
    }

    public Location saveLocation(LocationDto location) {
        log.debug("saveLocation: location={}", location);

        return locationRepository.save(fromLocation(location));
    }

    private LocationDto fromLocation(Location location) {
        return LocationDto.builder()
                .id(location.getExternalId())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .country(location.getCountry())
                .countryCode(location.getCountryCode())
                .build();
    }

    Location fromLocation(LocationDto location) {
        return Location.builder()
                .externalId(location.getId())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .country(location.getCountry())
                .countryCode(location.getCountryCode())
                .build();
    }
}
