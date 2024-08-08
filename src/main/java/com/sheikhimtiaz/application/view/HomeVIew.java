package com.sheikhimtiaz.application.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.annotation.security.PermitAll;

@Route("")
@AnonymousAllowed
public class HomeVIew extends VerticalLayout {


    public HomeVIew() {

        add(new H1("Welcome to the Weather App!"));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidth("100%");

        // add codes to navigate to location search view
        Button locationSearchButton = new Button("Location Search");
        locationSearchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        locationSearchButton.addClickListener(e -> {
            locationSearchButton.getUI().ifPresent(ui -> ui.navigate("search"));
        });
        horizontalLayout.add(locationSearchButton);


        add(horizontalLayout);

    }

}
