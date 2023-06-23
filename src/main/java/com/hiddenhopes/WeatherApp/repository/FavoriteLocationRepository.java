package com.hiddenhopes.WeatherApp.repository;

import com.hiddenhopes.WeatherApp.model.FavoriteLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {
    @Query("SELECT fl FROM FavoriteLocation fl WHERE fl.latitude = :latitude AND fl.longitude = :longitude")
    Optional<FavoriteLocation> findByCoordinates(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
