package com.example.application.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "daily_weather")
public class DailyWeather {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "city_name")
	private String city_name;
	@Column(name = "temperature")
	private String per_temperature;
	@Column(name = "temperature_unit")
	private String temperature_unit;
	@Column(name = "wind_speed")
	private String wind_speed;
	@Column(name = "wind_unit")
	private String wind_unit;
	@Column(name = "wind_direction")
	private String wind_direction;
	@Column(name = "is_day")
	private String dateType;
	@Column(name = "time")
	private String time;
	@Column(name = "location_timezone")
	private String locationTimezone;
	@Column(name = "date")
	private String date;

	public DailyWeather() {

		super();
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getPer_temperature() {
		return per_temperature;
	}

	public void setPer_temperature(String per_temperature) {
		this.per_temperature = per_temperature;
	}

	public String getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(String wind_speed) {
		this.wind_speed = wind_speed;
	}

	public String getWind_direction() {
		return wind_direction;
	}

	public void setWind_direction(String wind_direction) {
		this.wind_direction = wind_direction;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocationTimezone() {
		return locationTimezone;
	}

	public void setLocationTimezone(String locationTimezone) {
		this.locationTimezone = locationTimezone;
	}

	public Integer getId() {
		return id;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
