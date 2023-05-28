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
        Optional<FavouriteLocation> favouriteLocationOptional = favouriteLocationRepository.findByLocationId(favoriteLocation.getLocationId());
        if (favouriteLocationOptional.isPresent())
            return new ResponseEntity<>(List.of(favouriteLocationOptional.get()), HttpStatus.OK);
        else {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                throw new UsernameNotFoundException(ErrorMessages.NO_RECORD_FOUND_BY_ID + userId);
            }
            FavouriteLocation savedFavouriteLocation = favouriteLocationRepository.save(favouriteLocationEntity);
            User user = userOptional.get();
            List<FavouriteLocation> favouriteLocations = user.getFavouriteLocations();
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
     * @param locationId type Long
     * @return FavouriteLocation
     * @author raihan
     */
    @Override
    public ResponseEntity<FavouriteLocation> getFavouriteLocation(Long locationId) {

        Optional<FavouriteLocation> favouriteLocationOptional = favouriteLocationRepository.findByLocationId(locationId);
        if (favouriteLocationOptional.isEmpty())
            throw new LocationNotFoundException(ErrorMessages.NO_RECORD_FOUND_BY_ID + locationId);
        return new ResponseEntity<>(favouriteLocationOptional.get(), HttpStatus.OK);
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
