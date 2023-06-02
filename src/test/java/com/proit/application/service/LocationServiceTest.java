package com.proit.application.service;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.entity.Location;
import com.proit.application.data.repository.LocationRepository;
import com.proit.application.data.repository.UserFavLocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserFavLocationRepository userFavLocationRepository;

    @InjectMocks
    private LocationService locationService;

    @Test
    void testGetAllFavoriteLocationOfCurrentUser() {
        Pageable pageable = PageRequest.of(0, 2);
        String locationNameFilter = "Paris";
        Long userId = 1L;

        when(userService.getCurrentUserId()).thenReturn(userId);

        List<Long> favLocationIds = Arrays.asList(1L, 2L);
        when(userFavLocationRepository.findLocationIdsByUserId(userId)).thenReturn(favLocationIds);

        Location location1 = getLocation();
        Page<Location> locationPage = new PageImpl<>(Collections.singletonList(location1));
        when(locationRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(locationPage);

        Page<LocationDto> result = locationService.getAllFavoriteLocationOfCurrentUser(pageable, locationNameFilter);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(userService, times(1)).getCurrentUserId();
        verify(userFavLocationRepository, atLeastOnce()).findLocationIdsByUserId(userId);
        verify(locationRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testGetAllFavoriteLocationOfUser() {
        Pageable pageable = PageRequest.of(0, 2);
        String locationNameFilter = "Paris";
        Long userId = 1L;

        List<Long> favLocationIds = Arrays.asList(1L, 2L);
        when(userFavLocationRepository.findLocationIdsByUserId(userId)).thenReturn(favLocationIds);

        Location location1 = getLocation();
        Page<Location> locationPage = new PageImpl<>(Collections.singletonList(location1));
        when(locationRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(locationPage);

        Page<LocationDto> result = locationService.getAllFavoriteLocationOfUser(pageable, locationNameFilter, userId);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(userFavLocationRepository, times(1)).findLocationIdsByUserId(userId);
        verify(locationRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testGetLocationIdByExternalId() {
        Long externalId = 1L;
        Long locationId = 2L;

        when(locationRepository.findIdByExternalId(externalId)).thenReturn(locationId);

        Long result = locationService.getLocationIdByExternalId(externalId);

        assertEquals(locationId, result);
        verify(locationRepository, times(1)).findIdByExternalId(externalId);
    }

    @Test
    void testFindExternalIdsByLocationIds() {
        List<Long> locationIds = Arrays.asList(1L, 2L);
        Set<Long> externalIds = new HashSet<>(Arrays.asList(3L, 4L));

        when(locationRepository.findExternalIdsByIdIn(locationIds)).thenReturn(externalIds);

        Set<Long> result = locationService.findExternalIdsByLocationIds(locationIds);

        assertEquals(externalIds, result);
        verify(locationRepository, times(1)).findExternalIdsByIdIn(locationIds);
    }

    @Test
    void testFindLocationByExternalId() {
        long externalId = 2L;
        Location location = getLocation();

        when(locationRepository.findByExternalId(externalId)).thenReturn(location);

        Location result = locationService.findLocationByExternalId(externalId);

        assertEquals(location, result);
        verify(locationRepository, times(1)).findByExternalId(externalId);
    }

    @Test
    void testSaveLocation() {
        LocationDto location = LocationDto.builder().build();

        when(locationRepository.save(any())).thenReturn(getLocation());

        Location result = locationService.saveLocation(location);

        assertNotNull(result);
        verify(locationRepository, times(1)).save(any());
    }

    private Location getLocation(){
        return Location.builder()
                .name("Dhaka")
                .address("Dhaka, Dhaka Division, Bangladesh")
                .country("Bangladesh")
                .countryCode("BD")
                .latitude(23.8103)
                .longitude(90.4125)
                .build();
    }
}