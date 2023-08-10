package org.weather.app.ui;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import org.weather.app.service.WeatherForecastService;

public class DayForecastView extends Div {

  private final WeatherForecastService weatherForecastService;
  private final int currentDaysIndex;
  private final FormLayout mainLayout = new FormLayout();
  private final HourlyView hourlyView;

  public DayForecastView(WeatherForecastService weatherForecastService, int selectedDaysIndex) {
    this.weatherForecastService = weatherForecastService;
    this.currentDaysIndex = selectedDaysIndex;
    this.hourlyView = new HourlyView(this.weatherForecastService, this.currentDaysIndex);
    initMainLayout();
    drawView(mainLayout);
    add(mainLayout);
  }

  private void initMainLayout() {
    setId("DayForecastView-extends-Div");
    mainLayout.setId("DayForecastView-main");
    mainLayout.setWidthFull();
    mainLayout.setColspan(hourlyView, 3);
  }

  private void drawView(FormLayout formLayout) {
    formLayout.add(
        new TemperatureCard(
            this.weatherForecastService.buildTemperatureModel(this.currentDaysIndex)),
        new PrecipitationCard(this.weatherForecastService.findPrecipitation(this.currentDaysIndex)),
        new SunCard(this.weatherForecastService.buildSunModel(this.currentDaysIndex)),
        new UvIndexCard(this.weatherForecastService.findUvIndex(this.currentDaysIndex)),
        new WindCard(this.weatherForecastService.buildWindModel(this.currentDaysIndex)),
        new RainCard(this.weatherForecastService.buildRainModel(this.currentDaysIndex)),
        hourlyView);
    formLayout.setResponsiveSteps(
        new FormLayout.ResponsiveStep("0", 1),
        new FormLayout.ResponsiveStep("600px", 2),
        new FormLayout.ResponsiveStep("900px", 3));
  }
}
