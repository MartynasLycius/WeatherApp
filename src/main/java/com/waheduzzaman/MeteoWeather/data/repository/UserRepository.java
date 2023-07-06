package com.waheduzzaman.MeteoWeather.data.repository;

import com.waheduzzaman.MeteoWeather.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByName(String name);
}
