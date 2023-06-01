package com.example.application.backend.service;

import com.example.application.backend.model.City;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.backend.repository.CityRepository;

@Service
public class CityService {

	@Autowired
	CityRepository cityRepository;

	public ArrayList<City> getAllCity() {
		ArrayList<City> city = new ArrayList<>();
		cityRepository.findAll().forEach(city::add);
		return city;
	}

	public City getByCity(String city_name) {
		City city = cityRepository.findByCity_name(city_name);

		return city;
	}

}