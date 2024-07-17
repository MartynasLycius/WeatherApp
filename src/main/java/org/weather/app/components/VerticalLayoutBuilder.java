package org.weather.app.components;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;

import java.util.*;

public class VerticalLayoutBuilder {
    private Alignment alignItems = Alignment.CENTER;
    private JustifyContentMode justifyContentMode = JustifyContentMode.CENTER;
    private boolean padding = false;
    private boolean spacing = false;
    private String width = "80%";
    private String backgroundColor = "rgba(211, 196, 218, 0.5)";
    private String paddingTop = "0%";
    private List<String> classNames = new ArrayList<>();

    public VerticalLayoutBuilder setAlignItems(Alignment alignItems) {
        this.alignItems = alignItems;
        return this;
    }

    public VerticalLayoutBuilder setJustifyContentMode(JustifyContentMode justifyContentMode) {
        this.justifyContentMode = justifyContentMode;
        return this;
    }

    public VerticalLayoutBuilder setPadding(boolean padding) {
        this.padding = padding;
        return this;
    }

    public VerticalLayoutBuilder setSpacing(boolean spacing) {
        this.spacing = spacing;
        return this;
    }

    public VerticalLayoutBuilder setWidth(String width) {
        this.width = width;
        return this;
    }

    public VerticalLayoutBuilder setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public VerticalLayoutBuilder setPaddingTop(String paddingTop) {
        this.paddingTop = paddingTop;
        return this;
    }

    public VerticalLayoutBuilder setClassNames(String... classNames) {
        this.classNames.addAll(Arrays.asList(classNames));
        return this;
    }

    public VerticalLayout build() {
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(alignItems);
        layout.setJustifyContentMode(justifyContentMode);
        layout.setPadding(padding);
        layout.setSpacing(spacing);
        layout.setWidth(width);
        layout.getStyle().set("background-color", backgroundColor);
        layout.getStyle().set("padding-top", paddingTop);
        layout.addClassNames(classNames.toArray(new String[0]));
        return layout;
    }
}


