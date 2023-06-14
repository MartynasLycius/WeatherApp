package com.example.application.views.forecast;

import com.example.application.data.dto.meteo.WeatherForecastResponse;
import com.example.application.utils.DateTimeUtil;
import com.example.application.utils.WeatherCodeMap;
import com.example.application.views.common.ChartView;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;

import java.util.List;

public class HourlyWeatherForecastView extends Dialog {
    public HourlyWeatherForecastView(int dayIndex, WeatherForecastResponse weatherData) {
        addClassName("hourly-weather-forecast-modal");

        prepareHeader(dayIndex, weatherData);

        setWidth("80%");

        int start = dayIndex * 24;
        List<Double> temperature_2m = weatherData.getHourly().getTemperature_2m();

        prepareChartView(weatherData, temperature_2m, start);

        prepareHoursForeCast(weatherData, start, temperature_2m);
    }

    private void prepareHoursForeCast(WeatherForecastResponse weatherData, int start, List<Double> temperature_2m) {
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

    private void prepareHeader(int dayIndex, WeatherForecastResponse weatherData) {
        setHeaderTitle(DateTimeUtil.convertDateStringToDayAndDateString(weatherData.getDaily().getTime().get(dayIndex)) + " - Weather Forecast");

        prepareCloseButton();
    }

    private void prepareCloseButton() {
        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> this.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.getHeader().add(closeButton);
    }


    private static Div prepareRainAndWindColumn(WeatherForecastResponse weatherData, int i) {
        Div column = new Div();
        column.addClassName("column");

        column.add(prepareRainDiv(weatherData, i));

        column.add(prepareWindDiv(weatherData, i));

        return column;
    }


    private static Div prepareWindDiv(WeatherForecastResponse weatherData, int i) {
        Div windDiv = new Div();
        windDiv.addClassName("text");
        windDiv.add(
                new Html("<i class=\"fa-solid fa-wind\"></i>"),
                new Html(
                        String.format("<span>&nbsp;%d %s</span>",
                                weatherData.getHourly().getWindspeed_10m().get(i).intValue(),
                                weatherData.getHourly_units().getWindspeed_10m()
                        )
                )
        );
        return windDiv;
    }


    private static Div prepareRainDiv(WeatherForecastResponse weatherData, int i) {
        Div rainDiv = new Div();
        rainDiv.addClassName("text");
        rainDiv.add(
                new Html("<i class=\"fa-solid fa-umbrella\"></i>"),
                new Html(
                        String.format("<span>&nbsp;%d %s</span>",
                                weatherData.getHourly().getPrecipitation_probability().get(i),
                                weatherData.getHourly_units().getPrecipitation_probability()
                        )
                )
        );
        return rainDiv;
    }


    private static Div prepareTemperatureSpan(WeatherForecastResponse weatherData, List<Double> temperature_2m, int i) {
        Div temperatureDiv = new Div();
        temperatureDiv.addClassName("big-text");
        temperatureDiv.setText(
                String.format("%d %s",
                        temperature_2m.get(i).intValue(),
                        weatherData.getHourly_units().getTemperature_2m()
                )
        );
        return temperatureDiv;
    }


    private static Html prepareWeatherIcon(WeatherForecastResponse weatherData, int i) {
        return new Html(
                String.format("<i title=\"%s\" class=\"hour-weather-icon %s\"></i>",
                        WeatherCodeMap.getWeatherMessage(weatherData.getHourly().getWeathercode().get(i)),
                        WeatherCodeMap.getWeatherIcon(weatherData.getHourly().getWeathercode().get(i))
                )
        );
    }


    private static Div prepareTimeDiv(WeatherForecastResponse weatherData, int i) {
        String time = DateTimeUtil.convertDateTimeStringToTimeAmPmString(weatherData.getHourly().getTime().get(i));
        Div timeDiv = new Div();
        timeDiv.addClassNames("big-text", "time");
        timeDiv.setText(time);
        return timeDiv;
    }

    private void prepareChartView(WeatherForecastResponse weatherData, List<Double> temperature_2m, int start) {
        ChartView chartView = new ChartView(
                weatherData.getHourly().getTime().subList(start, start + 24),
                getChartData(weatherData, start),
                getChartLabels(weatherData)
        );
        add(chartView);
    }

    private List<List<Double>> getChartData(WeatherForecastResponse weatherData, int start) {
        return List.of(
                weatherData.getHourly().getTemperature_2m().subList(start, start + 24),
                weatherData.getHourly().getRain().subList(start, start + 24),
                weatherData.getHourly().getWindspeed_10m().subList(start, start + 24)
        );
    }

    private List<String> getChartLabels(WeatherForecastResponse weatherData) {
        return List.of(
                String.format("Temperature (%s)", weatherData.getHourly_units().getTemperature_2m()),
                String.format("Rain (%s)", weatherData.getHourly_units().getRain()),
                String.format("Wind (%s)", weatherData.getHourly_units().getWindspeed_10m())
        );
    }
}
