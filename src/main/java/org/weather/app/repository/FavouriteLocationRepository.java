package org.weather.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weather.app.domain.FavouriteLocation;
import org.weather.app.domain.User;

@Repository
public interface FavouriteLocationRepository extends JpaRepository<FavouriteLocation, Long> {

  List<FavouriteLocation> findAllByUser(User user);

  FavouriteLocation findByLatitudeAndLongitudeAndUser(Double latitude, Double longitude, User user);
}
