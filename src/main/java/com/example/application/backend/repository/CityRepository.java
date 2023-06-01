package com.example.application.backend.repository;

import com.example.application.backend.model.City;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

	public City findById(long id);

	@Query(value = "SELECT * FROM city a WHERE a.city_name = ?1", nativeQuery = true)
	public City findByCity_name(String city_name);

	@Query(value = "SELECT a.city_name FROM city a WHERE a.favourite = ?1", nativeQuery = true)
	public List<String> findCity_nameByFavourite(String favourite);

}
