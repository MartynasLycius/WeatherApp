package org.weather.app.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.weather.app.config.Constants;
import org.weather.app.service.WeatherForecastService;
import org.weather.app.service.dto.pojo.HourlyModel;

public class HourlyView extends VerticalLayout {

  private final WeatherForecastService weatherForecastService;
  private final int currentDaysIndex;
  private final Grid<HourlyModel> hourlyGrid;

  public HourlyView(WeatherForecastService weatherForecastService, int selectedDaysIndex) {
    this.weatherForecastService = weatherForecastService;
    this.currentDaysIndex = selectedDaysIndex;
    this.hourlyGrid = new Grid<>(HourlyModel.class);
    initStyles();
    add(
        new CardHeader(
            Constants.HOUR_24_IMAGE_URL,
            "24 hours forecast of Time, Temperature, Relative humidity, Apparent temperature, Precipitation, Rain, Wind speed"));
    add(hourlyGrid);

    hourlyGrid.addClassName("contact-grid");
    hourlyGrid.setSizeFull();
    hourlyGrid.setColumns(
        "time",
        "temperature",
        "relativeHumidity",
        "apparentTemperature",
        "precipitation",
        "rain",
        "windSpeed");
    hourlyGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    hourlyGrid.addClassName("shadow-m");
    hourlyGrid.addClassName("p-s");
    hourlyGrid.addClassName(LumoUtility.BorderRadius.LARGE);
    hourlyGrid.setItems(this.weatherForecastService.findHourlyForecast(this.currentDaysIndex));
  }

  private void initStyles() {
    addClassNames(LumoUtility.Background.BASE, LumoUtility.BorderRadius.LARGE);
    setWidthFull();
    setHeight("600px");
    getStyle().set("margin", "5px");
  }
}
