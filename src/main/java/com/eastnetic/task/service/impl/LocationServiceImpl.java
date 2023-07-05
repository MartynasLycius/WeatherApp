package com.eastnetic.task.service.impl;

import com.eastnetic.task.model.entity.UserFavLocations;
import com.eastnetic.task.model.dto.LocationDTO;
import com.eastnetic.task.model.dto.LocationResults;
import com.eastnetic.task.repository.UserFavLocationsRepo;
import com.eastnetic.task.service.LocationService;
import com.eastnetic.task.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;
import java.util.List;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    @Autowired
    WebClient webClient;
    @Autowired
    UserFavLocationsRepo userFavLocationsRepo;
    @Autowired
    UsersService usersService;
    @Value("${location.search.api.url}")
    private String locationUrl;

    /**
     * Get data from Location service call
     * @param cityName
     * @return LocationDTO
     * @throws
     */
    @Override
    public LocationDTO getLocations(String cityName) {
        LocationDTO locationList;
        String url = MessageFormat.format(this.locationUrl, cityName);
        try{
            locationList = this.webClient
                    .get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(LocationDTO.class)
                    .block();
            log.debug(locationList.toString());
        } catch (Exception e) {
            locationList = new LocationDTO();
        }
        return locationList;
    }

    /**
     * Save location data to favorites table
     * @param locationResults
     * @return
     * @throws
     */
    @Override
    public void saveFavorites(LocationResults locationResults) {
        UserFavLocations favLocations = new UserFavLocations();
        favLocations.setName(locationResults.getName());
        favLocations.setLatitude(String.valueOf(locationResults.getLatitude()));
        favLocations.setLongitude(String.valueOf(locationResults.getLongitude()));
        favLocations.setRegion(locationResults.getAdmin1());
        favLocations.setCountry(locationResults.getCountry());
        favLocations.setTimezone(locationResults.getTimezone());
        favLocations.setRawData(locationResults.toString());
        favLocations.setUserId(usersService.getCurrentUser().getId());

        userFavLocationsRepo.save(favLocations);
    }

    /**
     * Get favorite list from table by users
     * @param userId
     * @return List<UserFavLocations>
     * @throws
     */
    public List<UserFavLocations> getFavorites(int userId) {
        return userFavLocationsRepo.findByUserId(userId);
    }

    /**
     * Check if the location is saved in table or not
     * @param locationResults, userId
     * @return boolean
     * @throws
     */
    public boolean isSavedLocation(LocationResults locationResults, int userId) {
        try {
            UserFavLocations userFavLocations = userFavLocationsRepo.findByLatitudeAndLongitudeAndUserId(String.valueOf(locationResults.getLatitude()), String.valueOf(locationResults.getLongitude()), userId);
            if(userFavLocations != null){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     * delete favorite data from table
     * @param userFavLocations
     * @return
     * @throws
     */
    public boolean deleteFavorites(UserFavLocations userFavLocations){
        try {
            userFavLocationsRepo.delete(userFavLocations);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get favorite data by id
     * @param id
     * @return UserFavLocations
     * @throws
     */
    public UserFavLocations getFavoritesById(String id) {
        return userFavLocationsRepo.findById(Integer.parseInt(id)).orElse(null);
    }
}
