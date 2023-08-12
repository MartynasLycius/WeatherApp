package com.example.application.data.service;

import com.example.application.data.entity.Favourites;
import com.example.application.data.repository.FavouritesRepository;
import com.example.application.dto.GeoCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouritesService {

    private FavouritesRepository favouritesRepository;

    public FavouritesService(FavouritesRepository favouritesRepository) {
        this.favouritesRepository = favouritesRepository;
    }

    public Favourites makeFavorites(GeoCode geoCode){
        Favourites favourites = null;
        String userId = getUserId();
        if(geoCode == null){
            return favourites;
        }
        if(userId == null){
            return favourites;
        }
        favourites = getFavouriteByUserIdAndGeoCodeId(geoCode.getId());
        if(favourites == null){
            favourites = new Favourites();
            favourites.setCityName(geoCode.getName());
            favourites.setCountryName(geoCode.getCountry());
            favourites.setLatitude(geoCode.getLatitude());
            favourites.setLongitude(geoCode.getLongitude());
            favourites.setGeoCodeId(geoCode.getId());
            favourites.setUserId(userId);
        }
        favourites.setIsFavourite(1);
        favourites = favouritesRepository.save(favourites);
        return favourites;
    }

    public void removeFavourites(Long id){
        if(id == null){
            return;
        }
        Favourites favourites = favouritesRepository.findById(id).orElse(null);
        if (favourites == null){
            return;
        }
        favourites.setIsFavourite(0);
        favourites = favouritesRepository.save(favourites);
    }

    public Favourites getFavouriteByUserIdAndGeoCodeId(Long geoCodeId){
        String userId = getUserId();
        if(userId == null){
            return null;
        }
        Favourites favourites = favouritesRepository.findFavouriteByUserIdAndGeoCodeId(userId, geoCodeId).orElse(null);
        return favourites;
    }

    public List<Favourites> getFavouritesByUserIdAndFlag(String userId, int isFavourite){
        List<Favourites> favourites = new ArrayList();
        if(userId == null){
            return favourites;
        }
        favourites = favouritesRepository.findByUserIdAndFlag(userId, isFavourite);
        return favourites;
    }

    private String getUserId() {
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userId = authentication.getName();
        }
        return userId;
    }

}
