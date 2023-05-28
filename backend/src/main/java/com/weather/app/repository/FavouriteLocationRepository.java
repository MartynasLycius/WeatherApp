package com.weather.app.repository;

import com.weather.app.entity.FavouriteLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavouriteLocationRepository extends JpaRepository<FavouriteLocation,Long> {
    Optional<FavouriteLocation> findByLocationId(Long locationId);
}
