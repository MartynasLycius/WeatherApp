package com.waheduzzaman.MeteoWeather.views.home;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.data.dto.weather.Daily;
import com.waheduzzaman.MeteoWeather.data.dto.weather.Weather;
import com.waheduzzaman.MeteoWeather.service.impl.WeatherDetailsServiceImpl;
import com.waheduzzaman.MeteoWeather.utility.DateTimeUtility;
import com.waheduzzaman.MeteoWeather.utility.WMOCodeToOpenWeatherIcon;
import com.waheduzzaman.MeteoWeather.views.components.SeparatorWidget;

public class WeatherDetailsView extends Div {
    private final Div weekDaysScroller = new Div();
    private Div lastSelectedItem;
    private int currentSelectedItem;
    private WMOCodeToOpenWeatherIcon codeToOpenWeatherIcon;
    private Weather weather;
    private DateTimeUtility dateTimeUtility;
    private WeatherHighlightsView weatherHighlightsView;
    private WeatherDetailsServiceImpl weatherDetailsService;


    public WeatherDetailsView(WeatherDetailsServiceImpl weatherDetailsService, WMOCodeToOpenWeatherIcon codeToOpenWeatherIcon) {
        setClassName("weather-details-view");
        initObjects(weatherDetailsService, codeToOpenWeatherIcon);
        setupWeekdays();
        layoutViews();
    }

    private void initObjects(WeatherDetailsServiceImpl weatherDetailsService, WMOCodeToOpenWeatherIcon codeToOpenWeatherIcon) {
        this.weatherDetailsService = weatherDetailsService;
        this.codeToOpenWeatherIcon = codeToOpenWeatherIcon;
        this.weather = this.weatherDetailsService.getWeatherData();
        this.dateTimeUtility = new DateTimeUtility();
    }

    private void layoutViews() {
        add(new SeparatorWidget("week at a glance"));
        add(weekDaysScroller);
        add(new SeparatorWidget("highlights"));
        updateHighlights();
    }

    private void updateHighlights() {
        if (weatherHighlightsView != null)
            remove(weatherHighlightsView);
        weatherHighlightsView = new WeatherHighlightsView(this.weatherDetailsService, this.weather, currentSelectedItem);
        add(weatherHighlightsView);
    }

    private void setupWeekdays() {
        Daily daily = weather.getDaily();
        weekDaysScroller.setClassName("weekdays-scroller");
        for (int index = 0; index < daily.getTime().size(); index++) {
            Div day = getWeekDayCard(daily, index);
            if (index == 0) {
                day.addClassName("day-selected");
                handleDayClick(daily, day, index);
            }
            weekDaysScroller.add(day);
        }
    }

    private Div getWeekDayCard(Daily daily, int index) {
        Div div = new Div();
        div.setClassName("week-day-card");

        VerticalLayout dayCard = new VerticalLayout();
        HorizontalLayout tempRow = new HorizontalLayout();
        H6 dayTitle = new H6(dateTimeUtility.getWeekDayNameFromDateString(daily.getTime().get(index)));
        H4 tempHigh = new H4(String.format("%s°", daily.getTemperature2mMax().get(index)));
        H4 tempLow = new H4(String.format("%s°", daily.getTemperature2mMin().get(index)));
        Image iconImg = new Image(codeToOpenWeatherIcon.convertCodeToIconLink(daily.getWeathercode().get(index)), "sun");

        setStylesToDayCardComponents(dayTitle, tempHigh, tempLow, iconImg, dayCard);

        tempRow.add(
                tempHigh,
                tempLow
        );

        dayCard.add(
                dayTitle,
                iconImg,
                tempRow
        );

        dayCard.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, dayTitle, iconImg, tempRow);
        dayCard.setAlignItems(FlexComponent.Alignment.CENTER);
        div.add(dayCard);
        div.addClickListener(event -> {
            handleDayClick(daily, div, index);
        });
        return div;
    }

    private void handleDayClick(Daily daily, Div div, int index) {
        if (lastSelectedItem != null && lastSelectedItem != div) {
            lastSelectedItem.removeClassName("day-selected");
        }
        currentSelectedItem = index;
        lastSelectedItem = div;
        lastSelectedItem.addClassName("day-selected");
        updateHighlights();
    }

    private void setStylesToDayCardComponents(H6 dayTitle, H4 tempHigh, H4 tempLow, Image iconImg, VerticalLayout dayCard) {
        dayTitle.addClassNames(
                LumoUtility.FontWeight.BOLD,
                LumoUtility.FontSize.MEDIUM
        );

        tempHigh.addClassNames(
                LumoUtility.FontWeight.BOLD);
        tempLow.addClassNames(
                LumoUtility.FontWeight.BOLD, LumoUtility.TextColor.DISABLED);

        dayCard.addClassNames(
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Background.BASE,
                LumoUtility.BorderRadius.LARGE
        );

        iconImg.addClassNames(LumoUtility.Padding.NONE, LumoUtility.Margin.NONE);
    }
}