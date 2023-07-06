package com.waheduzzaman.MeteoWeather.views.components.cards;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.utility.DateTimeUtility;
import com.waheduzzaman.MeteoWeather.views.components.CardHeader;

public class SunScheduleCardWidget extends Div {

    private final String sunRiseTime, sunSetTime;
    private final DateTimeUtility dateTimeUtility;
    VerticalLayout vl = new VerticalLayout();

    public SunScheduleCardWidget(String sunRise, String sunSet) {
        this.sunRiseTime = sunRise;
        this.sunSetTime = sunSet;
        this.dateTimeUtility = new DateTimeUtility();

        initStyles();
        initCardVL();
        add(vl);
    }

    private void initCardVL() {
        vl.setHeightFull();
        vl.setAlignItems(FlexComponent.Alignment.START);
        vl.add(new CardHeader("Sun schedule", "https://www.svgrepo.com/show/407540/sun.svg"));
        vl.add(getSunScheduleContainer());
    }

    private VerticalLayout getSunScheduleContainer() {
        VerticalLayout column = new VerticalLayout();
        column.add(getRow(VaadinIcon.SUN_RISE, dateTimeUtility.getAMPMTimeFromDateString(sunRiseTime)),
                getRow(VaadinIcon.SUN_DOWN, dateTimeUtility.getAMPMTimeFromDateString(sunSetTime)));
        return column;
    }

    private HorizontalLayout getRow(VaadinIcon icon, String time) {
        HorizontalLayout row = new HorizontalLayout();
        row.add(new Icon(icon), new H4(time));
        return row;
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
