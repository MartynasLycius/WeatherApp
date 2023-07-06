package com.waheduzzaman.MeteoWeather.views.components.cards;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.views.components.CardHeader;

public class DailyRainSumCardWidget extends Div {

    private final Double totalRain;
    private final String unit;
    VerticalLayout vl = new VerticalLayout();

    public DailyRainSumCardWidget(Double totalRain, String unit) {
        this.totalRain = totalRain;
        this.unit = unit;

        initStyles();
        initCardVL();
        add(vl);
    }

    private void initCardVL() {
        vl.setHeightFull();
        vl.setAlignItems(FlexComponent.Alignment.START);
        vl.add(new CardHeader("Rain Sum", "https://www.svgrepo.com/show/298974/rain-storm.svg"));
        vl.add(getSunScheduleContainer());
    }

    private VerticalLayout getSunScheduleContainer() {
        VerticalLayout column = new VerticalLayout();
        Span speedLabel = new Span(String.format("%s %s", totalRain, unit));
        speedLabel.addClassNames(LumoUtility.FontSize.XXXLARGE, LumoUtility.FontWeight.EXTRABOLD);
        column.add(speedLabel);
        return column;
    }

    private void initStyles() {
        addClassNames(
                LumoUtility.Background.BASE,
                LumoUtility.BorderRadius.LARGE
        );
        setHeight("200px");
        getStyle().set("margin", "5px");
    }

}