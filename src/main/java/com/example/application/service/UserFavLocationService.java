package com.example.application.service;

import com.example.application.data.dto.LocationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserFavLocationService {

    Set<Long> getFavLocationsLocationIdsCurrentUser();

    Page<LocationDto> getAllFavoriteLocationOfCurrentUser(Pageable pageable, String value);

    void removeFromFavoriteLocation(Long id);

    void addNewUserFavoriteLocation(LocationDto locationDto);
}
