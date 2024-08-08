package com.sheikhimtiaz.application.repository;

import com.sheikhimtiaz.application.entity.FavoriteLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoriteLocationRepository extends JpaRepository<FavoriteLocation, Long> {
    List<FavoriteLocation> findByUsername(String username);
}

