package com.example.application.backend.repository;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.application.backend.model.DailyWeather;


@Repository
public interface DailyWeatherRepository extends CrudRepository<DailyWeather, Long>  {

	
	public DailyWeather findById(long id);
	public List<DailyWeather> findAll();
	
	

	@Query(value ="SELECT DISTINCT a.date FROM daily_weather a", nativeQuery = true)
	public ArrayList<String> findDistinctDate();

	@Query(value ="SELECT MAX(a.id) FROM daily_weather a WHERE a.date = ?1 ORDER BY id DESC", nativeQuery = true)
	public long findIdByDate(String date);
	
	@Query(value ="SELECT DISTINCT a.city_name FROM daily_weather a", nativeQuery = true)
	public ArrayList<String> findDistinctCity_name();
	
	
	@Query(value ="SELECT MAX(a.id) FROM daily_weather a WHERE a.city_name = ?1 ORDER BY id DESC", nativeQuery = true)
	public long findIdByCity_name(String cityName);
	

	
}
