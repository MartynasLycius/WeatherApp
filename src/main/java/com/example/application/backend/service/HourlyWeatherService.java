package com.example.application.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.backend.model.HourlyWeather;
import com.example.application.backend.repository.HourlyWeatherRepository;
import com.example.application.backend.utility.PropertyReader;

@Service
public class HourlyWeatherService {

	@Autowired
	HourlyWeatherRepository hourRepository;

	PropertyReader propertyReader = new PropertyReader();

	public HourlyWeatherService(HourlyWeatherRepository hourRepository) {

		super();
		this.hourRepository = hourRepository;
	}

	public ArrayList<HourlyWeather> getAllHour() {
		ArrayList<HourlyWeather> hour = new ArrayList<>();
		hourRepository.findAll().forEach(hour::add);
		return hour;
	}

	/**
	 * get hourly forecast.
	 *
	 * 
	 */

	public ArrayList<HourlyWeather> getHourlyForecast(String cityName, String date) {
		List<HourlyWeather> hourList = new ArrayList<>();
		HourlyWeather hour = new HourlyWeather();
		try {
			String[] totalHour = propertyReader.loadPropertiesValues("daily.hour").split(",");

			for (int i = 0; i < totalHour.length; i++) {
				long hourId = hourRepository.findIdByDate(cityName, date, totalHour[i]);
				hour = hourRepository.findById(hourId);
				hourList.add(hour);
			}

		} catch (NullPointerException ex) {

			System.out.print("Hourly weather Service  " + ex.getMessage());
		} catch (Exception exp) {

			System.out.print("Hourly weather Service " + exp.getMessage());
		}
		return (ArrayList<HourlyWeather>) hourList;
	}

}