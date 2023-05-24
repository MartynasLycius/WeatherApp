package com.example.application.services;

import com.example.application.data.entity.UserFavouritePlace;
import com.example.application.dto.CityGeoCoding;

import java.util.List;

public interface UserFavouritePlaceService {
    List<UserFavouritePlace> getCurrentUserFavouritePlaces();

    List<CityGeoCoding> getCurrentUserFavouriteCityGeoCoding();

    String makePlaceFavourite(CityGeoCoding cityGeoCoding);
}
