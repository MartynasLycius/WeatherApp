package com.sheikhimtiaz.application.repository;

import com.sheikhimtiaz.application.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByName(String name);
    Optional<Location> findByLatitudeAndLongitude(double latitude, double longitude);
}
