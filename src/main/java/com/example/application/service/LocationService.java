package com.example.application.service;

import com.example.application.data.dto.LocationDto;
import com.example.application.data.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface LocationService {

    Set<Long> findIdsByLocationIds(List<Long> userFavLocations);

    Long getIdByLocationId(Long locationId);

    Page<Location> findAllByLocationIdIn(List<Long> locationIds, Pageable pageable);

    LocationDto mapLocationToLocationDto(Location location);

    Location findLocationByLocationId(Long id);

    Location saveLocation(LocationDto location);
}
