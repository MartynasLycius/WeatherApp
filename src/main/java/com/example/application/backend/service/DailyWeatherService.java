package com.example.application.backend.service;

import com.example.application.backend.model.City;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.application.backend.model.HourlyWeather;
import com.example.application.backend.model.DailyWeather;
import com.example.application.backend.repository.CityRepository;
import com.example.application.backend.repository.HourlyWeatherRepository;
import com.example.application.backend.repository.DailyWeatherRepository;
import com.example.application.backend.utility.ApiUtility;
import org.json.JSONObject;

@Service
public class DailyWeatherService {

	@Autowired
	DailyWeatherRepository locationRepository;
	@Autowired
	HourlyWeatherRepository hourRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	CityService cityService;

	public DailyWeatherService(DailyWeatherRepository locationRepository) {

		super();
		this.locationRepository = locationRepository;
	}

	public ArrayList<DailyWeather> getAllLocation() {
		ArrayList<DailyWeather> location = new ArrayList<>();
		locationRepository.findAll().forEach(location::add);
		return location;
	}

	/**
	 * get favourite location daily forecast.
	 *
	 * 
	 */

	
	public ArrayList<DailyWeather> getMarkedDailyForecast() {
		ArrayList<DailyWeather> locationList = new ArrayList<>();
		DailyWeather location = new DailyWeather();
		try {
			List<String> favouriteCity = cityRepository.findCity_nameByFavourite("yes");

			for (int i = 0; i < favouriteCity.size(); i++) {

				long locationLd = locationRepository.findIdByCity_name(favouriteCity.get(i));

				location = locationRepository.findById(locationLd);
				locationList.add(location);
			}
		} catch (NullPointerException ex) {

			System.out.print("Mark Daily weather Service " + ex.getMessage());
		} catch (Exception exp) {

			System.out.print("Mark Daily weather Service" + exp.getMessage());
		}
		return locationList;
	}
	/**
	 * get daily forecast.
	 *
	 * 
	 */

	public ArrayList<DailyWeather> getDailyForecast() {
		ArrayList<DailyWeather> locationList = new ArrayList<>();
		DailyWeather location = new DailyWeather();
		try {
			List<String> city = cityRepository.findCity_nameByFavourite("no");
			for (int i = 0; i < city.size(); i++) {
				long locationLd = locationRepository.findIdByCity_name(city.get(i));
				location = locationRepository.findById(locationLd);
				locationList.add(location);
			}
		} catch (NullPointerException ex) {

			System.out.print("Daily weather Service " + ex.getMessage());
		} catch (Exception exp) {

			System.out.print("Daily weather Service" + exp.getMessage());
		}
		return locationList;
	}

	public void updatefavourite(String city_name, String type) {

		City city = cityRepository.findByCity_name(city_name);

		city.setFavourite(type);

		cityRepository.save(city);

	}

	public DailyWeather getById(long id) {
		return locationRepository.findById(id);
	}
	/**
	 * Parsing json value and save database.
	 *
	 * 
	 */

	public void weatherValueParse() {
		try {
			ArrayList<City> cityList = cityService.getAllCity();
			ApiUtility apiUtility = new ApiUtility();
			if (cityList.size() > 0) {

				for (int c = 0; c < cityList.size(); c++) {
					DailyWeather location = new DailyWeather();
					String latitude = cityList.get(c).getLatitude();
					String longitude = cityList.get(c).getLongitude();

					String responseBody = (String) apiUtility.weatherDataRetriver(latitude, longitude);

					JSONObject obj = new JSONObject(responseBody);

					location.setCity_name(cityList.get(c).getCity_name());

					JSONObject weatherBody = obj.getJSONObject("current_weather");

					location.setPer_temperature(weatherBody.get("temperature").toString());
					location.setWind_speed(weatherBody.get("windspeed").toString());
					location.setWind_direction(weatherBody.get("winddirection").toString());
					location.setTime(weatherBody.get("time").toString());

					String[] parts = weatherBody.get("time").toString().split("T");
					String date = parts[0];
					location.setDate(date);

					location.setLocationTimezone(obj.get("timezone").toString());

					JSONObject hourUnitBody = obj.getJSONObject("hourly_units");

					location.setTemperature_unit(hourUnitBody.get("temperature_2m").toString());
					location.setWind_unit(hourUnitBody.get("windspeed_10m").toString());

					locationRepository.save(location);
					JSONObject hourBody = obj.getJSONObject("hourly");

					String[] per_hour = hourBody.getJSONArray("time").toString().split(",");
					String[] per_temp = hourBody.getJSONArray("temperature_2m").toString().split(",");
					String[] per_humidity = hourBody.getJSONArray("relativehumidity_2m").toString().split(",");
					String[] per_wind = hourBody.getJSONArray("windspeed_10m").toString().split(",");
					String[] per_rain = hourBody.getJSONArray("rain").toString().split(",");

					for (int t = 0; t < per_hour.length; t++) {
						HourlyWeather hour = new HourlyWeather();
						String time = per_hour[t].toString();
						String temp = per_temp[t].toString();
						String humidity = per_humidity[t].toString();
						String wind = per_wind[t].toString();
						String rain = per_rain[t].toString();
						String[] hourPart = time.split("T");
						String dateTime = hourPart[0];
						String hourTime = hourPart[1];
						if (t == 0) {
							dateTime = dateTime.startsWith("[") ? dateTime.substring(1) : dateTime;
							hour.setDate(dateTime.substring(1));
							hour.setTime(hourTime);
							hour.setTempLevel(temp.startsWith("[") ? temp.substring(1) : temp);
							hour.setHumidityLevel(humidity.startsWith("[") ? humidity.substring(1) : humidity);
							hour.setWindSpeedLevel(wind.startsWith("[") ? wind.substring(1) : wind);
							hour.setRain(rain.startsWith("[") ? rain.substring(1) : rain);
							hour.setTemperature_unit(hourUnitBody.get("temperature_2m").toString());
							hour.setWind_unit(hourUnitBody.get("windspeed_10m").toString());
							hour.setHumidityUnit(hourUnitBody.get("relativehumidity_2m").toString());
							hour.setRain_unit(hourUnitBody.get("rain").toString());
						} else {
							hour.setCity_name(cityList.get(c).getCity_name());
							hour.setDate(dateTime.substring(1));
							hour.setTime(hourTime.substring(0, hourTime.length() - 1));
							hour.setTempLevel(temp);
							hour.setHumidityLevel(humidity);
							hour.setWindSpeedLevel(wind);
							hour.setRain(rain);
							hour.setTemperature_unit(hourUnitBody.get("temperature_2m").toString());
							hour.setWind_unit(hourUnitBody.get("windspeed_10m").toString());
							hour.setHumidityUnit(hourUnitBody.get("relativehumidity_2m").toString());
							hour.setRain_unit(hourUnitBody.get("rain").toString());
							hourRepository.save(hour);
						}
					}
				}

			}
		} catch (NullPointerException ex) {

			System.out.print("Daily weather Service json " + ex.getMessage());
		} catch (Exception exp) {

			System.out.print("Daily weather Service json" + exp.getMessage());
		}
	}
}
