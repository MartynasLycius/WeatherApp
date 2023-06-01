package com.proit.application.views.forecast;

import com.proit.application.data.dto.WeatherDataDto;
import com.proit.application.utils.DateTimeUtil;
import com.proit.application.utils.WeatherCodeLookupUtil;
import com.proit.application.views.common.ChartView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HourlyWeatherForecastView extends Dialog {
    public HourlyWeatherForecastView(int dayIndex, WeatherDataDto weatherData) {
        addClassName("hourly-weather-forecast-modal");

        prepareHeader(dayIndex, weatherData);

        setWidth("80%");

        int start = dayIndex * 24;
        List<Double> temperature_2m = weatherData.getHourly().getTemperature2m();

        prepareChartView(weatherData, start);

        prepareHoursForeCast(weatherData, start, temperature_2m);
    }

    private void prepareHoursForeCast(WeatherDataDto weatherData, int start, List<Double> temperature_2m) {
        Div hoursContainer = new Div();

        for (int i = start; i < start + 24; i++) {
            Div singleHour = new Div();
            singleHour.addClassName("single-hour");

            singleHour.add(prepareTimeDiv(weatherData, i));

            singleHour.add(prepareWeatherIcon(weatherData, i));

            singleHour.add(prepareTemperatureSpan(weatherData, temperature_2m, i));

            singleHour.add(prepareRainAndWindColumn(weatherData, i));

            hoursContainer.add(singleHour);
        }

        this.add(hoursContainer);
    }

    private void prepareHeader(int dayIndex, WeatherDataDto weatherData) {
        setHeaderTitle(DateTimeUtil.convertDateStringToDayAndDateString(weatherData.getDaily().getTime().get(dayIndex)) + " - Weather Forecast");

        prepareCloseButton();
    }

    private void prepareCloseButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> this.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.getHeader().add(closeButton);
    }

    @NotNull
    private static Div prepareRainAndWindColumn(WeatherDataDto weatherData, int i) {
        Div column = new Div();
        column.addClassName("column");

        column.add(prepareRainDiv(weatherData, i));

        column.add(prepareWindDiv(weatherData, i));

        return column;
    }

    @NotNull
    private static Div prepareWindDiv(WeatherDataDto weatherData, int i) {
        Div windDiv = new Div();
        windDiv.addClassName("text");
        windDiv.add(
                new Html("<i class=\"fa-solid fa-wind\"></i>"),
                new Html(
                        String.format("<span>&nbsp;%d %s</span>",
                                weatherData.getHourly().getWindSpeed10m().get(i).intValue(),
                                weatherData.getHourlyUnits().getWindSpeed10m()
                        )
                )
        );
        return windDiv;
    }

    @NotNull
    private static Div prepareRainDiv(WeatherDataDto weatherData, int i) {
        Div rainDiv = new Div();
        rainDiv.addClassName("text");
        rainDiv.add(
                new Html("<i class=\"fa-solid fa-umbrella\"></i>"),
                new Html(
                        String.format("<span>&nbsp;%s %s</span>",
                                weatherData.getHourly().getRain().get(i),
                                weatherData.getHourlyUnits().getRain()
                        )
                )
        );
        return rainDiv;
    }

    @NotNull
    private static Div prepareTemperatureSpan(WeatherDataDto weatherData, List<Double> temperature_2m, int i) {
        Div temperatureDiv = new Div();
        temperatureDiv.addClassName("big-text");
        temperatureDiv.setText(
                String.format("%d %s",
                        temperature_2m.get(i).intValue(),
                        weatherData.getHourlyUnits().getTemperature2m()
                )
        );
        return temperatureDiv;
    }

    @NotNull
    private static Html prepareWeatherIcon(WeatherDataDto weatherData, int i) {
        return new Html(
                String.format("<i title=\"%s\" class=\"hour-weather-icon %s\"></i>",
                        WeatherCodeLookupUtil.getWeatherMessage(weatherData.getHourly().getWeatherCode().get(i)),
                        WeatherCodeLookupUtil.getWeatherIcon(weatherData.getHourly().getWeatherCode().get(i))
                )
        );
    }

    @NotNull
    private static Div prepareTimeDiv(WeatherDataDto weatherData, int i) {
        String time = DateTimeUtil.convertDateTimeStringToTimeAmPmString(weatherData.getHourly().getTime().get(i));
        Div timeDiv = new Div();
        timeDiv.addClassNames("big-text", "time");
        timeDiv.setText(time);
        return timeDiv;
    }

    private void prepareChartView(WeatherDataDto weatherData, int start) {

        ChartView chartView = new ChartView(
                weatherData.getHourly().getTime().subList(start, start + 24),
                getChartData(weatherData, start),
                getChartLabels(weatherData)
        );
        add(chartView);
    }

    private List<List<Double>> getChartData(WeatherDataDto weatherData, int start) {
        return List.of(
                weatherData.getHourly().getTemperature2m().subList(start, start + 24),
                weatherData.getHourly().getRain().subList(start, start + 24),
                weatherData.getHourly().getWindSpeed10m().subList(start, start + 24)
        );
    }

    private List<String> getChartLabels(WeatherDataDto weatherData) {
        return List.of(
                String.format("Temperature (%s)", weatherData.getHourlyUnits().getTemperature2m()),
                String.format("Rain (%s)", weatherData.getHourlyUnits().getRain()),
                String.format("Wind (%s)", weatherData.getHourlyUnits().getWindSpeed10m())
        );
    }
}
