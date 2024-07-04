package com.nashid.weatherapp.views;

import com.nashid.weatherapp.core.notification.NotificationUtils;
import com.nashid.weatherapp.domain.Settings;
import com.nashid.weatherapp.enums.PrecipitationUnit;
import com.nashid.weatherapp.enums.TemperatureUnit;
import com.nashid.weatherapp.enums.TimeZone;
import com.nashid.weatherapp.enums.WindSpeedUnit;
import com.nashid.weatherapp.services.SettingsService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;

public class SettingsView extends Dialog implements BeforeEnterObserver {

    public SettingsView(SettingsService settingsService) {
        setHeaderTitle("Settings");
        setWidth("600px");
        setHeight("400px");

        Button saveButton = new Button("Save");
        saveButton.addClassName("action-button");
        Button closeButton = new Button("Close");
        closeButton.addClassName("action-button");
        closeButton.addClickListener(buttonClickEvent -> {
            close();
        });
        getFooter().add(saveButton);
        getFooter().add(closeButton);

        Settings settings = settingsService.getCurrentUserSettings();

        VerticalLayout verticalLayout = new VerticalLayout();
        Select<TimeZone> timeZoneSelect = new Select<>();
        timeZoneSelect.setLabel("Time Zone:");
        timeZoneSelect.setItems(TimeZone.values());
        timeZoneSelect.setValue(settings.getTimeZone());
        timeZoneSelect.addClassName("grid-item");
        verticalLayout.add(timeZoneSelect);
        Select<TemperatureUnit> temperatureUnitSelect = new Select<>();
        temperatureUnitSelect.setLabel("Temperature Unit:");
        temperatureUnitSelect.setItems(TemperatureUnit.values());
        temperatureUnitSelect.setValue(settings.getTemperatureUnit());
        temperatureUnitSelect.addClassName("grid-item");
        verticalLayout.add(temperatureUnitSelect);
        Select<WindSpeedUnit> windSpeedUnitSelect = new Select<>();
        windSpeedUnitSelect.setLabel("Wind Speed Unit:");
        windSpeedUnitSelect.setItems(WindSpeedUnit.values());
        windSpeedUnitSelect.setValue(settings.getWindSpeedUnit());
        windSpeedUnitSelect.addClassName("grid-item");
        verticalLayout.add(windSpeedUnitSelect);
        Select<PrecipitationUnit> precipitationUnitSelect = new Select<>();
        precipitationUnitSelect.setLabel("Precipitation Unit:");
        precipitationUnitSelect.setItems(PrecipitationUnit.values());
        precipitationUnitSelect.setValue(settings.getPrecipitationUnit());
        precipitationUnitSelect.addClassName("grid-item");
        verticalLayout.add(precipitationUnitSelect);
        verticalLayout.addClassName("grid-container");
        add(verticalLayout);

        saveButton.addClickListener(buttonClickEvent -> {
            System.out.println("Save values: " + timeZoneSelect.getValue().name() + " " + temperatureUnitSelect.getValue().name() + " " + windSpeedUnitSelect.getValue().name() + " " + precipitationUnitSelect.getValue().name());
            System.out.println("User : " + VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID").toString());
            Boolean success = settingsService.updateCurrentUserSettings(timeZoneSelect.getValue().name(), temperatureUnitSelect.getValue().name(), windSpeedUnitSelect.getValue().name(), precipitationUnitSelect.getValue().name());
            if (success) {
                NotificationUtils.showSuccessMessage("Settings has been updated successfully");
            }
            else {
                NotificationUtils.showErrorMessage("Unable to update Settings");
            }
            close();
        });

        open();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (VaadinSession.getCurrent().getAttribute("LOGGED_IN_USER_ID") == null) {
            beforeEnterEvent.forwardTo("login");
        }
    }
}
