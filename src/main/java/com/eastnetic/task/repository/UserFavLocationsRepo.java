package com.eastnetic.task.repository;

import com.eastnetic.task.model.dao.UserFavLocations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavLocationsRepo extends JpaRepository<UserFavLocations, Integer> {

    UserFavLocations findByLatitudeAndLongitudeAndUserId(String latitude, String longitude, int userId);
    List<UserFavLocations> findByUserId(int userId);
}