package com.eastnetic.application.locations.repository;

import com.eastnetic.application.locations.entity.FavouriteLocation;
import com.eastnetic.application.locations.entity.Location;
import com.eastnetic.application.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteLocationRepository extends JpaRepository<FavouriteLocation, Long> {

    FavouriteLocation findFirstByLocation(Location location);

    List<FavouriteLocation> findAllByUser(User user);

    FavouriteLocation findByUserAndLocation(User user, Location location);
}
