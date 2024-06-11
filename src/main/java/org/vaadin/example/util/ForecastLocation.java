package org.vaadin.example.util;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.vaadin.example.entity.Location;

import static org.vaadin.example.util.LabelValueUtils.createLabelValueComponent;

public class ForecastLocation {
    public static HorizontalLayout getLocationLayout(Location location) {

        HorizontalLayout locationLayout = new HorizontalLayout();
        locationLayout.setWidthFull();
        locationLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        locationLayout.add(createLabelValueComponent("Name", location.getName()));
        locationLayout.add(createLabelValueComponent("Country", location.getCountry()));
        locationLayout.add(createLabelValueComponent("Latitude", String.valueOf(location.getLatitude())));
        locationLayout.add(createLabelValueComponent("Longitude", String.valueOf(location.getLongitude())));

        return locationLayout;
    }
}
