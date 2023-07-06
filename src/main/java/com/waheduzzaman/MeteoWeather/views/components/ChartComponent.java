package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JavaScript("https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js")
@JavaScript("./src/chart.js")
public class ChartComponent extends VerticalLayout {
    private final Html htmlChart;
    private final List<String> xValues;
    private final List<String> yValues;

    public ChartComponent(String title, List<String> xValues, List<String> yValues) {
        this.htmlChart = new Html(Objects.requireNonNull(getClass().getResourceAsStream("/html/chart.html")));
        this.xValues = xValues.stream().map(s -> "\"" + s + "\"").collect(Collectors.toList());
        this.yValues = yValues.stream().map(s -> "\"" + s + "\"").collect(Collectors.toList());
        initStyles();
        add(new CardHeader(title, "https://www.svgrepo.com/show/345315/graph-chart-data-analytics-statistic-report-analysis.svg"));
        add(this.htmlChart);
        renderCharts();
    }

    private void renderCharts() {
        UI.getCurrent().getPage().executeJs(String.format("drawChart([%s],[%s]);", String.join(",", xValues), String.join(",", yValues)));
    }

    private void initStyles() {
        addClassNames(
                LumoUtility.Background.BASE,
                LumoUtility.BorderRadius.LARGE
        );
        htmlChart.getStyle().set("width", "100%");
        setWidthFull();
        setHeight("400px");
        getStyle().set("margin", "5px");
    }
}
