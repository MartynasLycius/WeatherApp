package com.proit.weather.view;

import com.proit.weather.service.SecurityService;
import com.proit.weather.utils.Messages;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import jakarta.validation.constraints.NotNull;

import static com.proit.weather.utils.KEY_CONSTANTS.*;

@Route("")
public class MainLayout extends AppLayout implements RouterLayout {
    @NotNull
    private final Div contentPane = new Div();

    public MainLayout() {
        addToNavbar(new DrawerToggle(), new H3(Messages.get(APPLICATION_HEADER_KEY)));
        final SideNav sideNav = new SideNav();
        sideNav.addItem(new SideNavItem(Messages.get(LOCATION_NAV_KEY), Locations.class, VaadinIcon.AREA_SELECT.create()));
        sideNav.addItem(new SideNavItem(Messages.get(FAVOURITE_NAV_KEY), Favourites.class, VaadinIcon.ARCHIVES.create()));
        addToDrawer(sideNav, new Button(Messages.get(SIGNOUT_NAV_KEY), VaadinIcon.SIGN_OUT.create(), e -> SecurityService.logout()));
        contentPane.setSizeFull();
        setContent(contentPane);
    }
}