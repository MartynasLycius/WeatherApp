package com.example.application.services;

import com.example.application.data.entity.AppUser;
import com.example.application.data.entity.UserFavouritePlace;
import com.example.application.dto.CityGeoCoding;
import com.example.application.repositories.UserFavouritePlaceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFavouritePlaceService {

    private final UserFavouritePlaceRepository userFavouritePlaceRepository;
    private final SecurityService securityService;
    private final AppUserService appUserService;


    public UserFavouritePlaceService(UserFavouritePlaceRepository userFavouritePlaceRepository, SecurityService securityService, AppUserService appUserService) {
        this.userFavouritePlaceRepository = userFavouritePlaceRepository;
        this.securityService = securityService;
        this.appUserService = appUserService;
    }

    public List<UserFavouritePlace> getCurrentUserFavouritePlaces() {
        AppUser appUser = this.getCurrentAppUser();
        if (appUser != null) {
            return this.userFavouritePlaceRepository.findAllByUserId(appUser.getId());
        }
        return null;
    }

    public List<CityGeoCoding> getCurrentUserFavouriteCityGeoCoding() {
        List<UserFavouritePlace> currentUserFavouritePlaces = this.getCurrentUserFavouritePlaces();
        List<CityGeoCoding> cityGeoCodingList = null;
        if (currentUserFavouritePlaces != null) {
            cityGeoCodingList = new ArrayList<>();
            for (UserFavouritePlace userFavouritePlace : currentUserFavouritePlaces) {
                try {
                    CityGeoCoding cityGeoCoding = new ObjectMapper().readValue(userFavouritePlace.getPlaceInfo(), CityGeoCoding.class);
                    cityGeoCodingList.add(cityGeoCoding);
                }
                catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return cityGeoCodingList;
    }

    public String makePlaceFavourite(CityGeoCoding cityGeoCoding) {
        AppUser appUser = this.getCurrentAppUser();
        if (appUser != null) {
            try {
                UserFavouritePlace userFavouritePlace = this.userFavouritePlaceRepository.findByPlaceId((long) cityGeoCoding.id);

                if (userFavouritePlace == null) {
                    String palaceInfoString = new ObjectMapper().writeValueAsString(cityGeoCoding);

                    userFavouritePlace = new UserFavouritePlace();
                    userFavouritePlace.setPlaceId((long) cityGeoCoding.id);
                    userFavouritePlace.setPlaceInfo(palaceInfoString);
                    userFavouritePlace.setUser(appUser);

                    this.userFavouritePlaceRepository.save(userFavouritePlace);
                    return cityGeoCoding.name + " Is Addd as Favourite Place";
                }
                else {
                    this.userFavouritePlaceRepository.delete(userFavouritePlace);
                    return cityGeoCoding.name + " Is Remove From Favourite Place";
                }
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "Failed to Make Place Favourite";
    }

    private AppUser getCurrentAppUser() {
        UserDetails authenticatedUser = securityService.getAuthenticatedUser();
        AppUser appUser = null;
        if (authenticatedUser != null) {
            appUser = this.appUserService.findUserByEmail(authenticatedUser.getUsername());
        }
        return appUser;
    }
}
