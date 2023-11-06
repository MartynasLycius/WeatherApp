package com.weather.application.service;

import com.weather.application.data.entity.Favourites;
import com.weather.application.data.repository.FavouritesRepository;
import com.weather.application.data.dto.GeoCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FavouritesService{

    private static final Logger LOGGER = Logger.getLogger(FavouritesService.class.getName());

    private FavouritesRepository favouritesRepository;

    public FavouritesService(FavouritesRepository favouritesRepository) {
        this.favouritesRepository = favouritesRepository;
    }

    public Favourites makeFavorites(GeoCode geoCode){
        try {
            LOGGER.log(Level.INFO, "makeFavorites request start with geoCode {0}", new Object[]{geoCode});
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
            LOGGER.log(Level.INFO, "makeFavorites request end with favourites {0}", new Object[]{favourites});
            return favourites;
        } catch (Exception e){
            String message = String.format("An Exception occurred while calling removeFavourites() %s", e.getMessage());
            LOGGER.log(Level.SEVERE, message);
            throw e;
        }
    }

    public void removeFavourites(Long id){
        try{
            LOGGER.log(Level.INFO, "removeFavourites request start with id {0}", new Object[]{id});
            if(id == null){
                return;
            }
            Favourites favourites = favouritesRepository.findById(id).orElse(null);
            if (favourites == null){
                return;
            }
            favourites.setIsFavourite(0);
            LOGGER.log(Level.INFO, "removeFavourites request end with favourites {0}", new Object[]{favourites});
            favouritesRepository.save(favourites);
        } catch (Exception e){
            String message = String.format("An Exception occurred while calling removeFavourites() %s", e.getMessage());
            LOGGER.log(Level.SEVERE, message);
            throw e;
        }
    }

    public Favourites getFavouriteByUserIdAndGeoCodeId(Long geoCodeId){
        try{
            LOGGER.log(Level.INFO, "getFavouriteByUserIdAndGeoCodeId request start with geoCodeId {0}", new Object[]{geoCodeId});
            String userId = getUserId();
            if(userId == null){
                return null;
            }
            Favourites favourites = favouritesRepository.findFavouriteByUserIdAndGeoCodeId(userId, geoCodeId).orElse(null);
            if(favourites == null){
                return  null;
            }
            LOGGER.log(Level.INFO, "getFavouriteByUserIdAndGeoCodeId request end with favourites {0}", new Object[]{favourites});
            return favourites;
        } catch (Exception e){
            String message = String.format("An Exception occurred while calling getFavouriteByUserIdAndGeoCodeId() %s", e.getMessage());
            LOGGER.log(Level.SEVERE, message);
            throw e;
        }
    }

    public List<Favourites> getFavouritesByUserIdAndFlag(int isFavourite){
        try {
            List<Favourites> favourites = null;
            String userId = getUserId();
            LOGGER.log(Level.INFO, "getFavouritesByUserIdAndFlag request start with userId {} and isFavourite {}", new Object[]{userId, isFavourite});
            if(userId == null){
                return favourites;
            }
            favourites = favouritesRepository.findByUserIdAndFlag(userId, isFavourite);
            LOGGER.log(Level.INFO, "getFavouritesByUserIdAndFlag request end with {}", new Object[]{favourites});
            return favourites;
        } catch (Exception e){
            String message = String.format("An Exception occurred while calling getFavouritesByUserIdAndFlag() %s", e.getMessage());
            LOGGER.log(Level.SEVERE, message);
            throw e;
        }
    }

    private String getUserId() {
        LOGGER.log(Level.INFO, "getUserId request start");
        String userId = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userId = authentication.getName();
        }
        LOGGER.log(Level.INFO, "getUserId request end with userId {}", new Object[]{userId});
        return userId;
    }

}
