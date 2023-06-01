package com.proit.application.views.forecast;

import com.proit.application.data.dto.DailyFullDataDto;
import com.proit.application.data.dto.LocationDto;
import com.proit.application.data.dto.WeatherDataDto;
import com.proit.application.utils.DateTimeUtil;
import com.proit.application.utils.WeatherCodeLookupUtil;
import com.proit.application.utils.WeatherIconUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
        H2 h2 = new H2();
        h2.setText(String.format("Weather Forecast for %d Days", weatherData.getDaily().getTime().size()));
        h2.setWidthFull();

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addClassNames(
                LumoUtility.BorderColor.ERROR
        );
        closeButton.addClickListener(e -> closeView());

        HorizontalLayout header = new HorizontalLayout();
        header.add(h2, closeButton);
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setWidthFull();

        add(header);

        List<DailyFullDataDto> fullDataDtos = convertWeatherDtoToDailyFullDataDto(weatherData);
        Grid<DailyFullDataDto> grid = new Grid<>(DailyFullDataDto.class, false);
        grid.addColumn(getDayAndDateRenderer())
                .setHeader("Day");
        grid.addColumn(getWeatherRenderer())
                .setHeader(createCenteredHeaderComponent("Weather"));
        grid.addColumn(dailyFullDataDto -> String.format("%s | %s %s", dailyFullDataDto.getTemperature2mMax(), dailyFullDataDto.getTemperature2mMin(), dailyFullDataDto.getTemperatureUnit()))
                        .setHeader("Temperature");
        grid.addColumn(dailyFullDataDto -> String.format("%s %s", dailyFullDataDto.getWindSpeed10mMax(), dailyFullDataDto.getWindSpeedUnit()))
                        .setHeader("Wind");
        grid.addColumn(dailyFullDataDto -> String.format("%s %s", dailyFullDataDto.getRainSum(), dailyFullDataDto.getRainUnit()))
                        .setHeader("Rain");

        grid.getColumns().forEach(column -> {
            column.setAutoWidth(true);
            column.setSortable(false);
        });
        grid.setItems(fullDataDtos);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.addClassNames(
                LumoUtility.Background.BASE
        );
        add(grid);
        grid.asSingleSelect().addValueChangeListener(e -> {
            if (e.getValue() == null) {
                return;
            }

            openModal(weatherData, e.getValue().getDay() - 1);
        });
        this.addClassNames(
                LumoUtility.BoxShadow.LARGE
        );
        log.debug("DailyWeatherForecastView populated with weather data.");
    }

    private static Renderer<DailyFullDataDto> getDayAndDateRenderer() {
        return LitRenderer.<DailyFullDataDto>of(
                "<vaadin-vertical-layout style=\"align-items: left;line-height: var(--lumo-line-height-s);\">"
                        + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\"> ${item.day} </span>"
                        + "    <span> ${item.date} </span>"
                        + "</vaadin-vertical-layout>"
        )
                .withProperty("day", dailyFullDataDto -> DateTimeUtil.convertDateStringToDayAndDateString(dailyFullDataDto.getDate()).split(",")[0])
                .withProperty("date", dailyFullDataDto -> DateTimeUtil.convertDateStringToDayAndDateString(dailyFullDataDto.getDate()).split(",")[1]);
    }

    private static Renderer<DailyFullDataDto> getWeatherRenderer() {
        return LitRenderer.<DailyFullDataDto>of(
                "<vaadin-vertical-layout style=\"align-items: center;line-height: var(--lumo-line-height-s);\">"
                        + "  <img style=\"margin-top: -10px\" src=\"${item.icon}\"></img>"
                        + "  <span style=\"margin-top: -15px\"> ${item.description} </span>"
                        + "</vaadin-vertical-layout>"
        )
                .withProperty("icon", dailyFullDataDto -> WeatherIconUtil.getWeatherIcon(dailyFullDataDto.getWeatherCode()))
                .withProperty("description", dailyFullDataDto -> WeatherCodeLookupUtil.getWeatherMessage(dailyFullDataDto.getWeatherCode()));
    }

    private void openModal(WeatherDataDto weatherData, int i) {
        var modal = new HourlyWeatherForecastView(i, weatherData);
        modal.open();
        log.debug("Opened HourlyWeatherForecastView modal for day: {}", i);
    }

    private List<DailyFullDataDto> convertWeatherDtoToDailyFullDataDto(WeatherDataDto weatherDataDto) {
        List<DailyFullDataDto> dailyFullDataDtoList = new ArrayList<>();
        for (int i = 0; i < weatherDataDto.getDaily().getWeatherCode().size(); i++) {
            DailyFullDataDto dailyFullDataDto = DailyFullDataDto.builder()
                    .day(i + 1)
                    .date(weatherDataDto.getDaily().getTime().get(i))
                    .weatherCode(weatherDataDto.getDaily().getWeatherCode().get(i))
                    .temperature2mMax(weatherDataDto.getDaily().getTemperature2mMax().get(i))
                    .temperature2mMin(weatherDataDto.getDaily().getTemperature2mMin().get(i))
                    .temperatureUnit(weatherDataDto.getDailyUnits().getTemperature2mMax())
                    .rainSum(weatherDataDto.getDaily().getRainSum().get(i))
                    .rainUnit(weatherDataDto.getDailyUnits().getRainSum())
                    .windSpeed10mMax(weatherDataDto.getDaily().getWindSpeed10mMax().get(i))
                    .windSpeedUnit(weatherDataDto.getDailyUnits().getWindSpeed10mMax())
                    .build();
            dailyFullDataDtoList.add(dailyFullDataDto);
        }

        return dailyFullDataDtoList;
    }

    private Component createCenteredHeaderComponent(String text) {
        Div headerComponent = new Div();
        headerComponent.setText(text);
        headerComponent.getStyle().set("text-align", "center");
        return headerComponent;
    }
}
