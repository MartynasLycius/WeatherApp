package com.example.application.views;

import com.example.application.service.UserService;
import com.example.application.views.favoritelocations.FavoriteLocationsView;
import com.example.application.views.searchlocation.SearchLocationView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.time.ZoneId;
import java.util.TimeZone;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private final UserService userService;
    private final AccessAnnotationChecker accessChecker;

    public MainLayout(UserService userService, AccessAnnotationChecker accessChecker) {
        this.userService = userService;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        H1 appName = new H1("Weather App");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();
        if (accessChecker.hasAccess(SearchLocationView.class)) {
            nav.addItem(new SideNavItem("Search Location", SearchLocationView.class, LineAwesomeIcon.SEARCH_LOCATION_SOLID.create()));

        }
        if (accessChecker.hasAccess(FavoriteLocationsView.class)) {
            nav.addItem(new SideNavItem("Favorite Locations", FavoriteLocationsView.class, LineAwesomeIcon.STAR_SOLID.create()));
        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        if (userService.isUserLoggedIn()) {

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(userService.getUserDetails().getUsername());
            div.add(new Icon("lumo", "dropdown"));
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                userService.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> {
            TimeZone uiTimeZone = TimeZone.getTimeZone(details.getTimeZoneId());
            VaadinSession session = VaadinSession.getCurrent();
            session.setAttribute(ZoneId.class, uiTimeZone.toZoneId());
        });
    }
}
