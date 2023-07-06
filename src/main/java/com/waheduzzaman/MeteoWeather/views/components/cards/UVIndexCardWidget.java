package com.waheduzzaman.MeteoWeather.views.components.cards;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.views.components.CardHeader;

public class UVIndexCardWidget extends Div {

    Double uvIndex;
    VerticalLayout vl = new VerticalLayout();

    public UVIndexCardWidget(Double uvIndex) {
        this.uvIndex = uvIndex;

        initStyles();
        initCardVL();
        add(vl);
        getUVIndexProgressbar();
    }

    private void initCardVL() {
        vl.setHeightFull();
        vl.setAlignItems(FlexComponent.Alignment.START);
        vl.add(new CardHeader("UV Index", "https://www.svgrepo.com/show/341277/uv-index-alt.svg"));
    }

    private void getUVIndexProgressbar() {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setMin(0);
        progressBar.setMax(11); // 43.3 is the maximum uv recorded in australia back in 2023 but 11 is considered the highest
        progressBar.setValue(uvIndex);

        Div progressBarLabelText = new Div();
        progressBarLabelText.setText(String.format("UV Index: %s %%", uvIndex));
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
