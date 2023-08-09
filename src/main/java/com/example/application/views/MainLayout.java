package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.ListView;
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

public class MainLayout extends AppLayout {
    private SecurityService securityService;

    public MainLayout(SecurityService securityService){
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
        RouterLink dashboardView = new RouterLink("Dashboard", DashboardView.class);

        addToDrawer(new VerticalLayout(
            listView,
            dashboardView
        ));
    }
}
