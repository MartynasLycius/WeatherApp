package com.weather.application.views;

import com.weather.application.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import java.util.logging.Logger;

public class MainLayout extends AppLayout{

    private static final Logger LOGGER = Logger.getLogger(MainLayout.class.getName());
    private SecurityService securityService;

    public MainLayout(SecurityService securityService){
        LOGGER.info("Initiate Main Layout");
        this.securityService = securityService;
        createHeader();
        createDrawre();
    }

    private void createHeader() {
        Image logoImage = new Image("images/logo.png", "Weather App");
        logoImage.setWidth("70px");
        logoImage.setHeight("70px");

        H1 logo = new H1("Weather App");

        logo.addClassNames("text-l", "m-m");

        Button logout = new Button("Logout", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logoImage, logo, logout);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawre() {
        RouterLink listView = new RouterLink("List", ListView.class);
        listView.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
            listView
        ));
    }
}
