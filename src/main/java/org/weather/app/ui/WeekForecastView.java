package org.weather.app.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.Map;
import java.util.Objects;
import org.weather.app.config.Utils;
import org.weather.app.service.WeatherForecastService;
import org.weather.app.service.dto.WeatherForecast;
import org.weather.app.service.dto.forecast.Daily;
import org.weather.app.service.dto.pojo.WmoCode;

public class WeekForecastView extends Div {
  private final Div weekDaysScroller = new Div();
  private Div lastSelectedItem;
  private int currentSelectedItem;
  private final WeatherForecast weatherForecast;
  private final WeatherForecastService weatherForecastService;
  private DayForecastView dayForecastView;
  private final Map<Integer, WmoCode> wmoCodeMap;
  private final H5 dynamic = new H5("");

  public WeekForecastView(
      WeatherForecastService weatherForecastService, Map<Integer, WmoCode> wmoCodeMap) {
    setClassName("week-forecast-view");
    this.weatherForecastService = weatherForecastService;
    this.weatherForecast = this.weatherForecastService.getStoredWeatherForecast();
    this.wmoCodeMap = wmoCodeMap;
    buildWeek();
    initLayout();
  }

  private void initLayout() {
    setId("WeekForecastView-extends-Div-rightLayout");
    weekDaysScroller.setId("WeekForecastView-weekDaysScroller");
    add(buildBarHeader("A week forecast day by day", "bar", false));
    add(weekDaysScroller);
    add(buildBarHeader("Day forecast details", "bar", true));
    updateDayForecast();
  }

  private Div buildBarHeader(String title, String cssClass, boolean isDynamic) {
    Div barDiv = new Div(new H5(title));
    if (isDynamic) {
      barDiv = new Div(dynamic);
    }
    barDiv.setClassName(cssClass);
    return barDiv;
  }

  private void buildWeek() {
    Daily daily = weatherForecast.getDaily();
    weekDaysScroller.setClassName("week-days-scroller");
    for (int index = 0; index < daily.getTime().size(); index++) {
      Div day = buildDayComponent(daily, index);
      if (index == 0) {
        day.addClassName("selected-day");
        onClickDay(day, index);
      }
      weekDaysScroller.add(day);
    }
  }

  private Div buildDayComponent(Daily daily, int index) {
    Div div = new Div();
    div.setClassName("day-card");

    VerticalLayout dayLayout = new VerticalLayout();
    HorizontalLayout temperatureLayout = new HorizontalLayout();
    Span dayTitle = new Span(Utils.getDateStringWithWeekDayName(daily.getTime().get(index)));
    H4 highTemperature = new H4(String.format("%s°C", daily.getTemperature2mMax().get(index)));
    H4 lowTemperature = new H4(String.format("%s°C", daily.getTemperature2mMin().get(index)));
    Image icon =
        new Image(
            Utils.findStaticImageUrl(daily.getWeatherCode().get(index), this.wmoCodeMap), "alt");

    setStylesToDayCard(dayTitle, highTemperature, lowTemperature, icon, dayLayout);
    temperatureLayout.add(highTemperature, lowTemperature);
    dayLayout.add(dayTitle, icon, temperatureLayout);

    dayLayout.setHorizontalComponentAlignment(
        FlexComponent.Alignment.CENTER, dayTitle, icon, temperatureLayout);
    dayLayout.setAlignItems(FlexComponent.Alignment.CENTER);
    div.add(dayLayout);
    div.addClickListener(event -> onClickDay(div, index));
    return div;
  }

  private void onClickDay(Div div, int index) {
    if (Objects.nonNull(lastSelectedItem) && lastSelectedItem != div) {
      lastSelectedItem.removeClassName("selected-day");
    }
    currentSelectedItem = index;
    lastSelectedItem = div;
    lastSelectedItem.addClassName("selected-day");
    dynamic.setText(
        "Forecast of the day "
            + lastSelectedItem.getComponentAt(0).getElement().getChild(0).getText());
    updateDayForecast();
  }

  private void setStylesToDayCard(
      Span dayTitle, H4 highTemperature, H4 lowTemperature, Image icon, VerticalLayout dayLayout) {
    dayTitle.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.FontSize.MEDIUM);
    highTemperature.addClassNames(
        LumoUtility.FontWeight.EXTRABOLD, LumoUtility.FontSize.XLARGE, "warm");
    lowTemperature.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.FontSize.MEDIUM, "cold");
    dayLayout.addClassNames(
        LumoUtility.Padding.MEDIUM, LumoUtility.Background.BASE, LumoUtility.BorderRadius.LARGE);
    icon.addClassNames(LumoUtility.Padding.NONE, LumoUtility.Margin.NONE);
  }

  private void updateDayForecast() {
    if (Objects.nonNull(dayForecastView)) {
      remove(dayForecastView);
    }
    dayForecastView = new DayForecastView(this.weatherForecastService, currentSelectedItem);
    add(dayForecastView);
  }
}
