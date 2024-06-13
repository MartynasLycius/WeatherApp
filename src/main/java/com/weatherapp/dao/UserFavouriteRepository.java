package com.weatherapp.dao;

import com.weatherapp.model.UserFavourites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 13 Jun, 2024
 */
public interface UserFavouriteRepository extends JpaRepository<UserFavourites, Integer> {
    @Query("select c from user_favourites c where c.user.id =:userId")
    List<UserFavourites> findAllByUserId(@Param("userId") int userId);

    @Query("select c from user_favourites c where c.latitude =:latitude and c.longitude =:longitude and c.user.id =:userId")
    UserFavourites get(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("userId") int userId);
}
