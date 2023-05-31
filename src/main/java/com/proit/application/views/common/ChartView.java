package com.proit.application.views.common;

import com.proit.application.utils.DateTimeUtil;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ChartView extends VerticalLayout {
    private static final String BAR_COLOR = "rgba(130, 205, 255, 0.6)";
    private static final String BORDER_COLOR = "rgba(35, 162, 247, 1)";

    private final Div chartDiv;
    private final String fullJsCode;

    public ChartView(List<String> labels, List<Double> values, String commonLabel) {
        chartDiv = new Div();
        fullJsCode = constructJsCode(labels, values, commonLabel);

        String script = "<script>" + fullJsCode + "</script>";
        chartDiv.getElement().setProperty("innerHTML", "<canvas id=\"myChart\"></canvas>" + script);
    }

    private String constructJsCode(List<String> labels, List<Double> values, String commonLabel) {
        String formattedLabels = formatLabels(labels);
        String formattedValues = formatValues(values);

        return String.format("var ctx = document.getElementById('myChart').getContext('2d');" +
                "var myChart = new Chart(ctx, {" +
                "type: 'bar'," +
                "data: {" +
                "labels: [%s]," +
                "datasets: [{" +
                "label: '%s'," +
                "data: [%s]," +
                "backgroundColor: '%s'," +
                "borderColor: '%s'," +
                "borderWidth: 1" +
                "}]" +
                "}," +
                "options: {" +
                "scales: {" +
                "y: {" +
                "beginAtZero: true" +
                "}" +
                "}" +
                "}" +
                "});", formattedLabels, commonLabel, formattedValues, BAR_COLOR, BORDER_COLOR);
    }

    private String formatLabels(List<String> labels) {
        return labels.stream()
                .map(DateTimeUtil::convertDateTimeStringToTimeAmPmString)
                .collect(Collectors.joining("','", "'", "'"));
    }

    private String formatValues(List<Double> values) {
        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        log.debug("Attaching chart to view");

        chartDiv.getElement().executeJs(fullJsCode);
        add(chartDiv);
        chartDiv.setSizeFull();
        this.setWidthFull();
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        log.debug("Detaching chart from view");

        chartDiv.getElement().executeJs("var chart = document.getElementById('myChart'); if (chart) { chart.getContext('2d').clearRect(0, 0, chart.width, chart.height); }");
        super.onDetach(detachEvent);
    }
}
