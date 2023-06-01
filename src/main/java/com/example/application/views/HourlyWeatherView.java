package com.example.application.views;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.backend.model.HourlyWeather;
import com.example.application.backend.service.HourlyWeatherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.DialogVariant;
import com.vaadin.flow.component.icon.Icon;
import com.example.application.views.grid.*;

public class HourlyWeatherView {

	@Autowired
	HourlyWeatherService hourService;

	/**
	 * Hourly weather report view.
	 *
	 * 
	 */

	public Dialog hourlyForecast(HourlyWeatherService hourService, String cityName, String date) {

		this.hourService = hourService;
		Dialog dialog = new Dialog();
		dialog.setHeaderTitle("Hourly Weather Update");
		PaginatedGrid<HourlyWeather> grid = new PaginatedGrid<>();
		grid.addColumn(HourlyWeather::getCity_name).setHeader("City Name").setSortable(true);
		grid.addColumn(HourlyWeather::getDate).setHeader("Date").setSortable(true).setFrozen(true).setAutoWidth(true)
				.setFlexGrow(0);
		grid.addColumn(HourlyWeather::getTime).setHeader("Hour").setSortable(true);
		grid.addColumn(HourlyWeather::getTempLevel).setHeader("Temperature").setSortable(true);
		grid.addColumn(HourlyWeather::getTemperature_unit).setHeader("Temperature Unit").setSortable(true);
		grid.addColumn(HourlyWeather::getHumidityLevel).setHeader("Humidity").setSortable(true);
		grid.addColumn(HourlyWeather::getHumidityUnit).setHeader("Humidity Level").setSortable(true);
		grid.addColumn(HourlyWeather::getRain).setHeader("Rain").setSortable(true);
		grid.addColumn(HourlyWeather::getRain_unit).setHeader("Rain Unit").setSortable(true);
		grid.addColumn(HourlyWeather::getWindSpeedLevel).setHeader("Wind Speed").setSortable(true);
		grid.addColumn(HourlyWeather::getWind_unit).setHeader("Wind Speed Unit").setSortable(true);
		grid.getDataProvider().refreshAll();
		grid.setItems(hourService.getHourlyForecast(cityName, date));
		grid.setPageSize(10);
		grid.setPaginatorSize(2);
		Button closeButton = new Button(new Icon("lumo", "cross"), (e) -> dialog.close());
		closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		dialog.getHeader().add(closeButton);
		dialog.addThemeVariants(DialogVariant.LUMO_NO_PADDING);
		dialog.setSizeFull();
		dialog.setModal(true);
		dialog.setDraggable(true);
		dialog.setResizable(true);
		dialog.add(grid);
		return dialog;
	}

}
