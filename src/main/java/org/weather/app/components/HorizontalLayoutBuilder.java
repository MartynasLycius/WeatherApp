package org.weather.app.components;

import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HorizontalLayoutBuilder {

    private JustifyContentMode justifyContentMode = JustifyContentMode.CENTER;
    private String width = "80%";
    private String backgroundColor = "rgba(211, 196, 218, 0.5)";
    private boolean padding = false;
    private boolean spacing = false;
    /*private Alignment alignItems = Alignment.CENTER;


    private String paddingTop = "0%";*/

    /*public HorizontalLayoutBuilder setAlignItems(Alignment alignItems) {
        this.alignItems = alignItems;
        return this;
    }*/

    public HorizontalLayoutBuilder setJustifyContentMode(JustifyContentMode justifyContentMode) {
        this.justifyContentMode = justifyContentMode;
        return this;
    }

    public HorizontalLayoutBuilder setPadding(boolean padding) {
        this.padding = padding;
        return this;
    }

    public HorizontalLayoutBuilder setSpacing(boolean spacing) {
        this.spacing = spacing;
        return this;
    }

    public HorizontalLayoutBuilder setWidth(String width) {
        this.width = width;
        return this;
    }

    public HorizontalLayoutBuilder setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    /*public HorizontalLayoutBuilder setPaddingTop(String paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }*/

    public HorizontalLayout build() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setJustifyContentMode(justifyContentMode);
        layout.getStyle().set("background-color", backgroundColor);
        layout.setWidth(width);
        layout.setPadding(padding);
        layout.setSpacing(spacing);
        return layout;
    }
}
