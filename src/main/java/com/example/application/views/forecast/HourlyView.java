package com.example.application.views.forecast;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.example.application.services.ForecastAPI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.example.application.data.HourlyForecast;
import com.example.application.data.Location;
import java.util.List;
  
public class HourlyView extends FormLayout {
    ForecastAPI forecast;
    Dialog dialog;
    LineChart chart = new LineChart();

    public HourlyView(ForecastAPI forecast, Dialog dialog) {
        this.forecast = forecast;
        this.dialog = dialog;
        
        this.configureLayout();
    }
    public void openAndLoadDialog(Location selectedLocation) {
        this.dialog.open();
        this.chart.generateChart(this.loadTempsString(selectedLocation));
    }
    private void configureLayout() {
        Button close = new Button("Close");
        HorizontalLayout horiz = new HorizontalLayout();
        horiz.setId("chartContainer");
        horiz.setHeight("420px");
        horiz.setWidth("500px");

        VerticalLayout layout = new VerticalLayout(horiz,close);
        close.setId("dialogButtonID");
        close.addClickListener(event -> {
            this.dialog.close();
        });
        layout.setId("verticalLayout");
        this.dialog.removeAll();
        this.dialog.add(layout); 
    }
    public HourlyForecast loadTemperatures(Location selectedLocation) {
        return this.forecast.getForecastHoursTemp(selectedLocation.getLatitude(),selectedLocation.getLongitude());
    }
    public String loadTempsString(Location selectedLocation) {
        HourlyForecast loadData = this.loadTemperatures(selectedLocation);

        String generatedJS = this.generateJSONString(loadData.getDaily().getDayHours(),loadData.getDaily().getTemperatures(),"Temperature");
        generatedJS += this.generateJSONString(loadData.getDaily().getDayHours(),loadData.getDaily().getWindSpeed(),"Wind");
        generatedJS += this.generateJSONString(loadData.getDaily().getDayHours(),loadData.getDaily().getRain(),"Rain");
        return generatedJS;
    }
    public String generateJSONString(List<String> hours, List<Double> datatwo, String name) {
        String jsCode = "";
        jsCode += "{";
        jsCode += "	name: '"+name+"',";
        if (name == "Temperature") {
            jsCode += "	type: 'line',axisYIndex: 1,";
            jsCode += "	yValueFormatString: '°C',";
        }
        if (name == "Wind") {
            jsCode += "	type: 'line',axisYIndex: 0,";
            jsCode += "	yValueFormatString: 'km/h',";
        }
        if (name == "Rain") { 
            jsCode += "	type: 'line',axisYIndex: 1,axisYType: 'secondary',";
            jsCode += "	yValueFormatString: 'mm',";
        }
        jsCode += "	showInLegend: true,"; 
        jsCode += "	dataPoints: [";
        for (Integer i=0;i < datatwo.size();i++) {
          jsCode += "		{ x: new Date('"+hours.get(i)+"'), y: "+datatwo.get(i)+" },";
        }
        jsCode += "	]";
        jsCode += "},";
        return jsCode;
    }
}
