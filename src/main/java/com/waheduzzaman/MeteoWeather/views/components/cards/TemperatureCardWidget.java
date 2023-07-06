package com.waheduzzaman.MeteoWeather.views.components.cards;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.data.model.ui.TemperatureCardViewModel;
import com.waheduzzaman.MeteoWeather.views.components.CardHeader;

public class TemperatureCardWidget extends Div {

    private TemperatureCardViewModel uiModel;

    VerticalLayout vl = new VerticalLayout();

    public TemperatureCardWidget(TemperatureCardViewModel temperatureCardViewModel) {
        this.uiModel = temperatureCardViewModel;

        initStyles();
        initCardVL();

        add(vl);
    }

    private void initCardVL() {
        vl.setHeightFull();
        vl.setAlignItems(FlexComponent.Alignment.START);
        vl.add(new CardHeader("Temperature", "https://www.svgrepo.com/show/475590/temperature.svg"));
        vl.add(getTemperatureContainer());
    }

    private VerticalLayout getTemperatureContainer() {
        VerticalLayout tempVL = new VerticalLayout();
        tempVL.setHeightFull();
        tempVL.setMargin(false);
        tempVL.setPadding(false);
        tempVL.setSpacing(false);
        tempVL.setWidthFull();
        tempVL.add(getTemperatureRow());
        tempVL.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        return tempVL;
    }

    private HorizontalLayout getTemperatureRow() {
        HorizontalLayout row = new HorizontalLayout();
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        row.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        row.add(getTemperatureLabelWithIcon(
                        new Icon(VaadinIcon.ARROW_UP), uiModel.getTemperatureHigh(), LumoUtility.FontSize.XXXLARGE, LumoUtility.FontWeight.EXTRABOLD),
                getTemperatureLabelWithIcon(new Icon(VaadinIcon.ARROW_DOWN), uiModel.getTemperatureLow(), LumoUtility.FontSize.XLARGE));
        row.setWidthFull();

        return row;
    }

    private HorizontalLayout getTemperatureLabelWithIcon(Icon icon, Double temp, String... styles) {
        HorizontalLayout highTempRow = new HorizontalLayout();
        Span tempH = new Span(String.format("%s%s", temp.toString(), uiModel.getUnit()));
        tempH.addClassNames(styles);
        highTempRow.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, icon, tempH);
        highTempRow.add(icon, tempH);

        return highTempRow;
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
