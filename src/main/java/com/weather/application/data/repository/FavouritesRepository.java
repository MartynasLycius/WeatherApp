package com.weather.application.data.repository;

import com.weather.application.data.entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Long> {

    @Query("select f from Favourites f where f.userId = :userId and f.geoCodeId = :geoCodeId")
    Optional<Favourites> findFavouriteByUserIdAndGeoCodeId(@Param("userId") String userId, @Param("geoCodeId") Long geoCodeId);


    @Query("select f from Favourites f where f.userId = :userId and f.isFavourite = :isFavourite")
    List<Favourites> findByUserIdAndFlag(@Param("userId") String userId, @Param("isFavourite") int isFavourite);

}
