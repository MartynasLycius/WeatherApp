package com.example.application.views.list;

import com.example.application.dto.GeoCode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
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

    private GeoCode geoCode;

    Span forecastHead;

    Span longLag;

    public Forecast(){
        addClassName("forecast-view");

        forecastHead = new Span(" Daily Forecast");
        forecastHead.addClassNames("text-xl", "mt-m");

        longLag = new Span();
        longLag.addClassNames("text-xl", "mt-m");

        add(
           createButtonLayout(),
           forecastHead,
           longLag
        );

    }

    public void setGeoCode(GeoCode geoCode) {
        this.geoCode = geoCode;
        forecastHead.setText(geoCode.getName() + " | " + geoCode.getCountry()  + ": Daily Forecast");
        longLag.setText(geoCode.getLongitude() + " : " + geoCode.getLatitude());
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickShortcut(Key.ENTER);

        return new HorizontalLayout(save);
    }


}
