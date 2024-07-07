package com.proit.weather.proit.weather.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proit.weather.proit.weather.model.FavoriteLocation;

public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {
    List<FavoriteLocation> findByUserId(String userId);
}

