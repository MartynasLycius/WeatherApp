package com.example.application.service.impl;

import com.example.application.data.dto.LocationDto;
import com.example.application.data.entity.Location;
import com.example.application.data.repository.LocationRepository;
import com.example.application.service.LocationService;
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
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public Long getIdByLocationId(Long locationId) {
        return locationRepository.findIdByLocationId(locationId);
    }

    public Set<Long> findIdsByLocationIds(List<Long> locationIds) {
        return locationRepository.findLocationIdsByIdIn(locationIds);
    }

    public Location findLocationByLocationId(Long locationId) {
        return locationRepository.findByLocationId(locationId);
    }


    public Location saveLocation(LocationDto location) {
        return locationRepository.save(mapLocationDtoToLocation(location));
    }

    public LocationDto mapLocationToLocationDto(Location location) {
        return LocationDto.builder()
                .id(location.getLocationId())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .country(location.getCountry())
                .countryCode(location.getCountryCode())
                .build();
    }

    public Location mapLocationDtoToLocation(LocationDto location) {
        return Location.builder()
                .locationId(location.getId())
                .name(location.getName())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .address(location.getAddress())
                .country(location.getCountry())
                .countryCode(location.getCountryCode())
                .build();
    }

    public Page<Location> findAll(Specification<Location> specification, Pageable pageable) {
        return locationRepository.findAll(specification, pageable);
    }
}
