package com.waheduzzaman.MeteoWeather.views.home;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.waheduzzaman.MeteoWeather.data.dto.weather.Weather;
import com.waheduzzaman.MeteoWeather.service.impl.WeatherDetailsServiceImpl;
import com.waheduzzaman.MeteoWeather.views.components.ChartComponent;
import com.waheduzzaman.MeteoWeather.views.components.HourlyListView;
import com.waheduzzaman.MeteoWeather.views.components.cards.*;


public class WeatherHighlightsView extends Div {
    private WeatherDetailsServiceImpl weatherDetailsService;
    private FormLayout widgetContainerFL;
    private ChartComponent chartComponent;
    private HourlyListView hourlyListView;
    private int currentDayIndex;

    public WeatherHighlightsView(WeatherDetailsServiceImpl weatherDetailsService, Weather weather, int currentDayIndex) {
        initObjects(weatherDetailsService, weather, currentDayIndex);
        addWidgetCards(widgetContainerFL);
        configureFormLayout();
        add(widgetContainerFL);
    }

    private void configureFormLayout() {
        widgetContainerFL.setWidthFull();
        widgetContainerFL.setColspan(chartComponent, 3);
        widgetContainerFL.setColspan(hourlyListView, 3);
    }

    private void initObjects(WeatherDetailsServiceImpl weatherDetailsService, Weather weather, int currentDayIndex) {
        this.weatherDetailsService = weatherDetailsService;
        this.currentDayIndex = currentDayIndex;
        this.widgetContainerFL = new FormLayout();
        this.chartComponent = new ChartComponent("Hourly (Temperature)", weatherDetailsService.getHourlyTimeStampForChart(currentDayIndex), weatherDetailsService.getHourlyTemperatureListForChart(currentDayIndex));
        this.hourlyListView = new HourlyListView(weatherDetailsService, currentDayIndex);
    }

    private void addWidgetCards(FormLayout formLayout) {
        formLayout.add(
                new TemperatureCardWidget(weatherDetailsService.getTemperatureCardViewModel(currentDayIndex)),
                new PrecipitationCardWidget(weatherDetailsService.getWeatherData().getDaily().getPrecipitationProbabilityMax().get(currentDayIndex)),
                new SunScheduleCardWidget(weatherDetailsService.getWeatherData().getDaily().getSunrise().get(currentDayIndex), weatherDetailsService.getWeatherData().getDaily().getSunset().get(currentDayIndex)),
                new UVIndexCardWidget(weatherDetailsService.getWeatherData().getDaily().getUvIndexMax().get(currentDayIndex)),
                new WindStatusCardWidget(weatherDetailsService.getWeatherData().getDaily().getWindspeed10mMax().get(currentDayIndex), weatherDetailsService.getWeatherData().getDailyUnits().getWindspeed10mMax()),
                new DailyRainSumCardWidget(weatherDetailsService.getWeatherData().getDaily().getRainSum().get(currentDayIndex), weatherDetailsService.getWeatherData().getDailyUnits().getRainSum()),
                chartComponent,
                hourlyListView
        );
        formLayout.setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("600px", 2),
                new ResponsiveStep("900px", 3));
        ;
    }
}
