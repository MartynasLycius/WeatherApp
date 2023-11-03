package com.example.application.data;


import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteLocationRepository extends JpaRepository<Location, Long>
{

}