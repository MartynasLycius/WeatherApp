package com.weather.app.service.impl;

import com.weather.app.config.ErrorMessages;
import com.weather.app.constants.Constants;
import com.weather.app.entity.FavouriteLocation;
import com.weather.app.entity.User;
import com.weather.app.exceptions.LocationNotFoundException;
import com.weather.app.model.FavouriteLocationRequestModel;
import com.weather.app.model.LocationModel;
import com.weather.app.model.LocationResponseModel;
import com.weather.app.repository.FavouriteLocationRepository;
import com.weather.app.repository.UserRepository;
import com.weather.app.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final FavouriteLocationRepository favouriteLocationRepository;
    private final UserRepository userRepository;

    /**
     * method for getting location by city
     *
     * @param city type String
     * @return LocationResponseModel
     * @author raihan
     */
    @Override
    public ResponseEntity<LocationResponseModel> getLocation(String city) {
        WebClient webClient = WebClient.create(Constants.LOCATION_BASE_URL);
        String uri = Constants.LOCATION_URI.replaceAll(Constants.CITY_PARAM, city);
        LocationResponseModel locationResponseModel = webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(LocationResponseModel.class))
                .doOnError(this::errorHappened)
                .block();
        return new ResponseEntity<>(locationResponseModel, HttpStatus.OK);
    }

    /**
     * method for adding favourite location
     *
     * @param favoriteLocation type FavouriteLocationRequestModel
     * @param userId           type Long
     * @return List of FavouriteLocation
     * @author raihan
     */
    @Override
    public ResponseEntity<List<FavouriteLocation>> setFavoriteLocation(FavouriteLocationRequestModel favoriteLocation, Long userId) {
        ModelMapper modelMapper = new ModelMapper();
        FavouriteLocation favouriteLocationEntity = modelMapper.map(favoriteLocation, FavouriteLocation.class);
        User user = userRepository.findById(userId).orElseThrow();
        List<FavouriteLocation> favouriteLocations = user.getFavouriteLocations();
        boolean flag= favouriteLocations.stream().anyMatch(favouriteLocation -> Objects.equals(favouriteLocation.getLocationId(), favouriteLocationEntity.getLocationId()));
        if (flag)
            return new ResponseEntity<>(favouriteLocations, HttpStatus.OK);
        else {
            FavouriteLocation savedFavouriteLocation = favouriteLocationRepository.save(favouriteLocationEntity);
            favouriteLocations.add(savedFavouriteLocation);
            user.setFavouriteLocations(favouriteLocations);
            userRepository.save(user);
            return new ResponseEntity<>(favouriteLocations, HttpStatus.CREATED);
        }
    }

    /**
     * method for get location by id
     *
     * @param locationId type String
     * @return LocationModel
     * @author raihan
     */
    @Override
    public ResponseEntity<LocationModel> getLocationById(String locationId) {
        WebClient webClient = WebClient.create(Constants.LOCATION_BASE_URL);
        String uri = Constants.LOCATION_URI_BY_ID.replaceAll(Constants.ID_PARAM, locationId);
        LocationModel locationModel = webClient.get()
                .uri(uri)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(LocationModel.class))
                .doOnError(this::errorHappened)
                .block();
        return new ResponseEntity<>(locationModel, HttpStatus.OK);
    }

    /**
     * method for getting favourite location
     *
     * @param userId type Long
     * @return FavouriteLocation
     * @author raihan
     */
    @Override
    public ResponseEntity<List<FavouriteLocation>> getFavouriteLocation(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND_BY_ID+userId);
        List<FavouriteLocation> favouriteLocations = user.get().getFavouriteLocations();
        return new ResponseEntity<>(favouriteLocations, HttpStatus.OK);
    }

    /**
     * throw error while webClient gets any error
     *
     * @param error type Throwable
     * @author raihan
     */
    private void errorHappened(Throwable error) {
        throw new RuntimeException(error.getLocalizedMessage());
    }


}
