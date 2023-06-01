package com.proit.application.data.repository;

import com.proit.application.data.entity.UserFavLocation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavLocationRepository extends JpaRepository<UserFavLocation, Long>, JpaSpecificationExecutor<UserFavLocation> {
    @Query("SELECT ufl.locationId FROM UserFavLocation ufl WHERE ufl.userId = :userId")
    List<Long> findLocationIdsByUserId(@Param("userId") Long userId);

    @Transactional
    void deleteByUserIdAndLocationId(long userId, long locationId);
}
