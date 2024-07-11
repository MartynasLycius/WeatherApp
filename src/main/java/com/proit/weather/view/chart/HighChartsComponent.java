package com.proit.weather.view.chart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import org.apache.commons.lang3.StringUtils;


@Tag("highcharts-component")
@JsModule("./highcharts-component.js")
@CssImport("./chart.css")
public class HighChartsComponent<T> extends Div {
    private static final String CHART_ID = "chart-container";
    private static final String FIGURE_CLASS_NAME = "highcharts-figure";
    private static final String HIGHCHART_COMPONENT = "highcharts-component";
    private static final String INITIALIZE_SCRIPT = "$0.Meteogram(%s, '%s')";
    private final Object object;

    public HighChartsComponent(T object) {
        this.object = object;
        Div loading = new Div();
        loading.setId("loading");

        Div chartDiv = new Div();
        chartDiv.setId(CHART_ID);
        chartDiv.add(loading);

        Figure figure = new Figure();
        figure.setClassName(FIGURE_CLASS_NAME);
        figure.add(chartDiv);

        chartDiv.getElement().setAttribute("is", HIGHCHART_COMPONENT);
        getElement().getStyle().set("width", "100%");
        getElement().getStyle().set("display", "flex");
        getElement().getStyle().set("justify-content", "center");
        addAttachListener(attachEvent -> onDOMContentLoaded());
        add(figure);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
    }

    private void onDOMContentLoaded() {
        String json = StringUtils.EMPTY;
        try {
            ObjectMapper mapper = JsonMapper.builder().findAndAddModules().build();
            ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            System.out.println(exception.getMessage());
        }
        getElement().executeJs(String.format(INITIALIZE_SCRIPT, json, CHART_ID));
    }
}
