package com.example.application.backend.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.application.backend.model.City;
import com.example.application.backend.model.HourlyWeather;

@Repository
public interface HourlyWeatherRepository extends CrudRepository<HourlyWeather, Long> {

	public HourlyWeather findById(long id);

	@Query(value = "SELECT DISTINCT h.time FROM hourly_weather h", nativeQuery = true)
	public ArrayList<String> findDistinctTime();

	@Query(value = "SELECT MAX(h.id) FROM hourly_weather h WHERE h.city_name = ?1 and h.date = ?2 and h.time = ?3  ORDER BY id DESC", nativeQuery = true)
	public long findIdByDate(String city_name, String date, String time);

}
