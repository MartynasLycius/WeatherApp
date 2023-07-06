package com.waheduzzaman.MeteoWeather.views.components.cards;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.views.components.CardHeader;

public class PrecipitationCardWidget extends Div {

    Double precipitation;
    VerticalLayout vl = new VerticalLayout();

    public PrecipitationCardWidget(Double precipitation) {
        this.precipitation = precipitation;

        initStyles();
        initCardVL();
        add(vl);
        getPrecipitationProgressbar();
    }

    private void initCardVL() {
        vl.setHeightFull();
        vl.setAlignItems(FlexComponent.Alignment.START);
        vl.add(new CardHeader("Precipitation", "https://www.svgrepo.com/show/281232/rain-forecast.svg"));
    }

    private void getPrecipitationProgressbar() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setMin(0);
        progressBar.setMax(100);
        progressBar.setValue(precipitation);

        Div progressBarLabelText = new Div();
        progressBarLabelText.setText(String.format("Precipitation: %s %%", precipitation));
        FlexLayout progressBarLabel = new FlexLayout();
        progressBarLabel.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        progressBarLabel.add(progressBarLabelText);

        vl.add(progressBarLabel, progressBar);
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
