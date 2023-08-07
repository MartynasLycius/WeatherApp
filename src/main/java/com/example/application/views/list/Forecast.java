package com.example.application.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class Forecast extends VerticalLayout {

    Button save = new Button("Favourite");

    public Forecast(){
        addClassName("forecast-view");

        H1 forecastHead = new H1("Daily Forecast");

        add(
           createButtonLayout(),
           forecastHead
        );

    }

    public void setForecast(Object forecast, String forecastType){
//        this.forecast = forecast;
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        return new HorizontalLayout(save);
    }


}
