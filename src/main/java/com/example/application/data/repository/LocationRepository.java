package com.example.application.data.repository;

import com.example.application.data.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>, JpaSpecificationExecutor<Location> {
    Location findByLocationId(long locationId);

    @Query("SELECT l.locationId FROM Location l WHERE l.id IN :locationIds")
    Set<Long> findLocationIdsByIdIn(List<Long> locationIds);

    @Query("SELECT l.id FROM Location l WHERE l.locationId = :locationId")
    Long findIdByLocationId(Long locationId);

    Page<Location> findAllByIdIn(List<Long> locationIds, Pageable pageable);

}
