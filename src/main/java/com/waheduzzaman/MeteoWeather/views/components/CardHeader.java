package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class CardHeader extends HorizontalLayout {

    String iconUrl;
    String title;

    public CardHeader(String title, String iconUrl) {
        this.iconUrl = iconUrl;
        this.title = title;
        Span cardTitle = new Span(title);
        cardTitle.addClassNames(LumoUtility.FontWeight.BOLD, LumoUtility.TextColor.DISABLED);
        Image image = new Image(iconUrl, title);
        image.setHeight("24px");
        image.setWidth("24px");
        setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        setWidthFull();
        add(cardTitle);
        add(image);
    }
}
