package com.eastnetic.application.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

    public MainLayout() {

        addToNavbar(new DrawerToggle(), createTopBarSpan());

        addToDrawer(createSidebarLayout());
    }

    private Span createTopBarSpan() {

        Span title = new Span("Weather App");
        title.addClassName("blue-span");

        return title;
    }

    private VerticalLayout createSidebarLayout() {

        VerticalLayout menuLayout = new VerticalLayout();

        RouterLink homeLink = new RouterLink("Home", LocationSearchView.class);
        RouterLink locationsLink = new RouterLink("Favourite Locations", FavouriteLocationsView.class);

        menuLayout.add(homeLink, locationsLink);

        return menuLayout;
    }
}