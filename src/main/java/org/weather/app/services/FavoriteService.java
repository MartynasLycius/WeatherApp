package org.weather.app.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.weather.app.entities.FavoriteCity;
import org.weather.app.entities.User;
import org.weather.app.repositories.FavoriteCityRepository;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FavoriteService {

    @Inject
    FavoriteCityRepository favoriteCityRepository;

    public void save(FavoriteCity favoriteCity) {
        favoriteCityRepository.save(favoriteCity);
    }

    public void delete(FavoriteCity favoriteCity) {
        favoriteCityRepository.delete(favoriteCity);
    }

    public FavoriteCity findCityByLatLon(String latitude, String longitude){
        return favoriteCityRepository.findCityByLatLong(latitude, longitude);
    }

    public FavoriteCity findCityByUserAndCityName(User user, String cityName){
        return favoriteCityRepository.findCityByUserIdAndCityName(user, cityName);
    }

    public List<FavoriteCity> findAllFavoriteCityByUserId(UUID id){
        return favoriteCityRepository.findFavoriteCitiesByUserId(id);
    }
}
