package com.eastnetic.application.locations.repository;

import com.eastnetic.application.locations.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByReferenceIdAndReferenceSource(String id, String source);

}
