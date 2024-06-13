package com.weatherapp.dao;

import com.weatherapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select c from user c where lower(c.userName)  = :userName ")
    User search(@Param("userName") String userName);
}
