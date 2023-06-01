package com.example.application.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "hourly_weather")
public class HourlyWeather {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "time")
	private String time;
	@Column(name = "date")
	private String date;
	@Column(name = "city_name")
	private String city_name;
	@Column(name = "humidity_level")
	private String humidityLevel;
	@Column(name = "humidity_unit")
	private String humidityUnit;
	@Column(name = "temp_level")
	private String tempLevel;
	@Column(name = "temperature_unit")
	private String temperature_unit;
	@Column(name = "windspeed_level")
	private String windSpeedLevel;
	@Column(name = "wind_unit")
	private String wind_unit;
	@Column(name = "rain")
	private String rain;
	@Column(name = "rain_unit")
	private String rain_unit;

	public String getHumidityLevel() {
		return humidityLevel;
	}

	public void setHumidityLevel(String humidityLevel) {
		this.humidityLevel = humidityLevel;
	}

	public String getTempLevel() {
		return tempLevel;
	}

	public void setTempLevel(String tempLevel) {
		this.tempLevel = tempLevel;
	}

	public String getWindSpeedLevel() {
		return windSpeedLevel;
	}

	public void setWindSpeedLevel(String windSpeedLevel) {
		this.windSpeedLevel = windSpeedLevel;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRain() {
		return rain;
	}

	public void setRain(String rain) {
		this.rain = rain;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getHumidityUnit() {
		return humidityUnit;
	}

	public void setHumidityUnit(String humidityUnit) {
		this.humidityUnit = humidityUnit;
	}

	public String getTemperature_unit() {
		return temperature_unit;
	}

	public void setTemperature_unit(String temperature_unit) {
		this.temperature_unit = temperature_unit;
	}

	public String getWind_unit() {
		return wind_unit;
	}

	public void setWind_unit(String wind_unit) {
		this.wind_unit = wind_unit;
	}

	public String getRain_unit() {
		return rain_unit;
	}

	public void setRain_unit(String rain_unit) {
		this.rain_unit = rain_unit;
	}

}
