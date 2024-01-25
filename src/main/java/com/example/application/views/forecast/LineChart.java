package com.example.application.views.forecast;

import com.vaadin.flow.component.UI;

public class LineChart { 
    public LineChart() {
        UI.getCurrent().getPage()
        .addJavaScript("./frontend/canvasjs.min.js");
    }
    public void generateChart(String data) {
        String jsExecute = "var chart = new CanvasJS.Chart('chartContainer', {";
        jsExecute += "animationEnabled: true,";
        jsExecute += "title:{";
        jsExecute += "	text: ''";
        jsExecute += "},";
        jsExecute += "axisY:[";
        jsExecute += "	{title:'Wind',suffix: 'kmh'},";
        jsExecute += "	{title:'Temperature',suffix: '°C'}";
        jsExecute += "],";
        jsExecute += "axisY2: {";
        jsExecute += "	title: 'Rain',";
        jsExecute += "	suffix: ' mm'";
        jsExecute += "},";
        jsExecute += "legend:{";
        jsExecute += "	cursor: 'pointer',";
        jsExecute += "	fontSize: 16";
        jsExecute += "},";
        jsExecute += "toolTip:{";
        jsExecute += "	shared: true";
        jsExecute += "},";
        jsExecute += "data: ["+data+"]";
        jsExecute += "});";
        jsExecute += "chart.render();";

        UI.getCurrent().getPage().executeJs(
            jsExecute
        );
    }
}
