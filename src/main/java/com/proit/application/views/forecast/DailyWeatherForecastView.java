package com.proit.application.views.forecast;

import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.dto.WeatherDataDto;
import com.proit.application.utils.DateTimeUtil;
import com.proit.application.utils.WeatherCodeLookupUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class DailyWeatherForecastView extends VerticalLayout {
    private LocationDto location;

    public DailyWeatherForecastView() {
        addClassName("forecast-container");
        setWidthFull();

        log.debug("DailyWeatherForecastView initialized.");
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public void setVisibility(boolean visible, WeatherDataDto weatherData) {
        if (visible && (location == null || weatherData == null)) {
            log.warn("Unable to set visibility due to missing location or weather data.");
            return;
        } else {
            location = null;
            log.debug("Location reset.");
        }

        setVisible(visible);
        log.debug("DailyWeatherForecastView visibility set to: {}", visible);

        if (visible) {
            removeAll();
            log.debug("Populating weather forecast...");
            populateWeatherForecast(weatherData);
        }
    }

    public void closeView() {
        setVisible(false);
        removeAll();
        log.debug("DailyWeatherForecastView closed.");
    }

    private void populateWeatherForecast(WeatherDataDto weatherData) {
        Div container = prepareForecastContainer(weatherData);

        Button closeBtn = prepareCloseButton();

        VerticalLayout verticalLayout = new VerticalLayout(container, closeBtn);
        verticalLayout.setAlignItems(Alignment.STRETCH);
        verticalLayout.setFlexGrow(1, container);
        verticalLayout.setSizeFull();

        add(verticalLayout);
        log.debug("DailyWeatherForecastView populated with weather data.");
    }

    private Div prepareForecastContainer(WeatherDataDto weatherData) {
        var container = new Div();
        container.addClassName("daily-weather-forecast-container");

        for (int i = 0; i < 7; i++) {
            var dayCard = new Div();
            dayCard.addClassName("day-card");
            int finalI = i;
            dayCard.addClickListener(e -> openModal(weatherData, finalI));
            var dayCardContent = prepareDayCardContent(weatherData, i);
            dayCard.add(dayCardContent);
            container.add(dayCard);
        }

        return container;
    }

    private Button prepareCloseButton() {
        Button closeBtn = new Button("Close");
        closeBtn.addClickListener(e -> closeView());
        closeBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return closeBtn;
    }

    private Div prepareDayCardContent(WeatherDataDto weatherData, int dayIndex) {
        var dayCardContent = new Div();
        dayCardContent.addClassName("day-card-content");
        dayCardContent.add(prepareDateSpan(weatherData, dayIndex));
        dayCardContent.add(prepareWeatherIconAndDescLayout(weatherData, dayIndex));
        dayCardContent.add(prepareSunriseSunsetSpan(weatherData, dayIndex));
        dayCardContent.add(prepareMinMaxTempSpan(weatherData, dayIndex));
        dayCardContent.add(preparePrecipitationSpan(weatherData, dayIndex));
        dayCardContent.add(prepareWindSpeedSpan(weatherData, dayIndex));
        return dayCardContent;
    }

    private Component prepareWindSpeedSpan(WeatherDataDto weatherData, int dayIndex) {
        var windSpeedSpan = new Span();
        windSpeedSpan.addClassName("white-text");
        windSpeedSpan.setTitle("Wind Speed");
        windSpeedSpan.add(
                new Html("<i class=\"fa-solid fa-wind\"></i>"),
                new Html(
                        String.format("<span>&nbsp;%s&nbsp;%s</span>",
                                weatherData.getDaily().getWindspeed10mMax().get(dayIndex),
                                weatherData.getDailyUnits().getWindSpeed10mMax()
                        )
                )
        );

        return windSpeedSpan;
    }

    private Component preparePrecipitationSpan(WeatherDataDto weatherData, int dayIndex) {
        var precipitationSpan = new Span();
        precipitationSpan.addClassName("white-text");
        precipitationSpan.add(
                new Html("<i class=\"fa-solid fa-umbrella\"></i>"),
                new Html(
                        String.format("<span title=\"Precipitation Probability\">&nbsp;%s&nbsp;%s&nbsp;&nbsp;</span>",
                                weatherData.getDaily().getPrecipitationProbabilityMax().get(dayIndex),
                                weatherData.getDailyUnits().getPrecipitationProbabilityMax()
                        )
                ),
                new Html("<i class=\"fa-solid fa-cloud-rain\"></i>"),
                new Html(
                        String.format("<span title=\"Total Rain\">&nbsp;%s&nbsp;%s</span>",
                                weatherData.getDaily().getRainSum().get(dayIndex),
                                weatherData.getDailyUnits().getRainSum()
                        )
                )

        );

        return precipitationSpan;
    }

    private Component prepareMinMaxTempSpan(WeatherDataDto weatherData, int dayIndex) {
        var minMaxTempSpan = new Span();
        minMaxTempSpan.addClassName("white-text");
        minMaxTempSpan.setTitle("Daily Minimum and Maximum Temperature");
        minMaxTempSpan.add(
                new Html("<i class=\"fa-solid fa-temperature-arrow-down\"></i>"),
                new Html(
                        String.format("<span>&nbsp;%s&nbsp;%s&nbsp;&nbsp;</span>",
                                weatherData.getDaily().getTemperature2mMin().get(dayIndex),
                                weatherData.getDailyUnits().getTemperature2mMin()
                        )
                ),
                new Html("<i class=\"fa-solid fa-temperature-arrow-up\"></i>"),
                new Html(
                        String.format("<span>&nbsp;%s&nbsp;%s</span>",
                                weatherData.getDaily().getTemperature2mMax().get(dayIndex),
                                weatherData.getDailyUnits().getTemperature2mMax()
                        )
                )
        );

        return minMaxTempSpan;
    }

    private Component prepareSunriseSunsetSpan(WeatherDataDto weatherData, int dayIndex) {
        var sunriseSunsetSpan = new Span();
        sunriseSunsetSpan.addClassName("white-text");
        sunriseSunsetSpan.setTitle("Sunrise / Sunset");
        sunriseSunsetSpan.add(
                new Html("<i class=\"wi wi-sunrise\"></i>"),
                new Html(
                        String.format("<span>&nbsp;&nbsp;%s&nbsp;&nbsp;</span>",
                                DateTimeUtil.convertDateTimeStringToTimeAmPmString(weatherData.getDaily().getSunrise().get(dayIndex))
                        )
                ),
                new Html("<i class=\"wi wi-sunset\"></i>"),
                new Html(
                        String.format("<span>&nbsp;&nbsp;%s</span>",
                                DateTimeUtil.convertDateTimeStringToTimeAmPmString(weatherData.getDaily().getSunset().get(dayIndex))
                        )
                )
        );

        return sunriseSunsetSpan;
    }

    private Component prepareWeatherIconAndDescLayout(WeatherDataDto weatherData, int dayIndex) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Html weatherIcon = prepareWeatherIcon(weatherData, dayIndex);

        Span weatherDescription = prepareWeatherDescription(weatherData, dayIndex);

        horizontalLayout.add(weatherIcon, weatherDescription);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.AROUND);
        horizontalLayout.addClassName("mb-s");

        return horizontalLayout;
    }

    @NotNull
    private static Span prepareWeatherDescription(WeatherDataDto weatherData, int dayIndex) {
        Span weatherDescription = new Span();
        weatherDescription.addClassName("weather-description");
        weatherDescription.setText(WeatherCodeLookupUtil.getWeatherMessage(weatherData.getDaily().getWeatherCode().get(dayIndex)));
        return weatherDescription;
    }

    @NotNull
    private static Html prepareWeatherIcon(WeatherDataDto weatherData, int dayIndex) {
        Html weatherIcon = new Html(String.format("<i class=\"day-weather-icon %s\"></i>", WeatherCodeLookupUtil.getWeatherIcon(weatherData.getDaily().getWeatherCode().get(dayIndex))));
        weatherIcon.getElement().setProperty("title", WeatherCodeLookupUtil.getWeatherMessage(weatherData.getDaily().getWeatherCode().get(dayIndex)));
        return weatherIcon;
    }

    private Component prepareDateSpan(WeatherDataDto weatherData, int dayIndex) {
        var daySpan = new Span();
        daySpan.addClassName("date");
        daySpan.setText(DateTimeUtil.convertDateStringToDayAndDateString(weatherData.getDaily().getTime().get(dayIndex)));

        return daySpan;
    }

    private void openModal(WeatherDataDto weatherData, int i) {
        var modal = new HourlyWeatherForecastView(i, weatherData);
        modal.open();
        log.debug("Opened HourlyWeatherForecastView modal for day: {}", i);
    }
}
