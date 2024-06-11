package org.vaadin.example.util;

import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LabelValueUtils {
    public static VerticalLayout createLabelValueComponent(String labelText, String valueText) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.getStyle().set("padding-top", "0");

        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("color", "gray");
        label.getStyle().set("font-size", "0.9em");

        NativeLabel value = new NativeLabel(valueText);
        value.getStyle().set("color", "black");
        value.getStyle().set("font-size", "1em");

        layout.add(label, value);

        return layout;
    }
}
