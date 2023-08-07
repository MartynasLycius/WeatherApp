package com.example.application.views.list;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Forecast extends VerticalLayout {

    Icon favoriteIcon = VaadinIcon.HEART.create();
    Button save = new Button(favoriteIcon);

    public Forecast(){
        addClassName("forecast-view");

        Span forecastHead = new Span("Daily Forecast");
        forecastHead.addClassNames("text-xl", "mt-m");

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
