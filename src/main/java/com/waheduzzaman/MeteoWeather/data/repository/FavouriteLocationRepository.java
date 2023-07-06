package com.waheduzzaman.MeteoWeather.data.repository;

import com.waheduzzaman.MeteoWeather.data.entity.FavouriteLocation;
import com.waheduzzaman.MeteoWeather.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteLocationRepository extends JpaRepository<FavouriteLocation, Long> {
    List<FavouriteLocation> findAllByUser(User user);

    FavouriteLocation findByLatitudeAndLongitudeAndUser(Double latitude, Double longitude, User user);
    
    int countAllByUser(User user);
}
