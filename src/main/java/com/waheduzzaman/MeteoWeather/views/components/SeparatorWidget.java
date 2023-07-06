package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;

public class SeparatorWidget extends Div {

    private String sectionLabel = "";

    public SeparatorWidget() {
        init();
    }

    public SeparatorWidget(String sectionLabel) {
        this.sectionLabel = sectionLabel;
        init();
    }

    public void setLabel(String label) {
        this.sectionLabel = label;
        init();
    }

    private void init() {
        setClassName("separator-band");
        removeAll();
        add(new H5(sectionLabel));
    }
}
