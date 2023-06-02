package com.proit.application.views;

import com.proit.application.components.appnav.AppNav;
import com.proit.application.components.appnav.AppNavItem;
import com.proit.application.data.entity.User;
import com.proit.application.security.AuthenticatedUser;
import com.proit.application.views.favoritelocations.FavoriteLocationsView;
import com.proit.application.views.searchlocation.SearchLocationView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.jetbrains.annotations.NotNull;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Optional;

public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
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

    private AppNav createNavigation() {
        AppNav nav = new AppNav();

        if (accessChecker.hasAccess(SearchLocationView.class)) {
            nav.addItem(new AppNavItem("Search Location", SearchLocationView.class,
                    LineAwesomeIcon.SEARCH_LOCATION_SOLID.create()));

        }
        if (accessChecker.hasAccess(FavoriteLocationsView.class)) {
            nav.addItem(new AppNavItem("Favorite Locations", FavoriteLocationsView.class,
                    LineAwesomeIcon.STAR_SOLID.create()));

        }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            MenuBar userMenu = getUserMenu(user);

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @NotNull
    private MenuBar getUserMenu(User user) {
        MenuBar userMenu = new MenuBar();
        userMenu.setThemeName("tertiary-inline contrast");

        MenuItem userName = userMenu.addItem("");
        userName.add(getNameDiv(user));
        userName.getSubMenu().addItem("Sign out", e -> authenticatedUser.logout());
        return userMenu;
    }

    @NotNull
    private static Div getNameDiv(User user) {
        Div div = new Div();
        div.add(user.getName());
        div.add(new Icon("lumo", "dropdown"));
        div.addClassName(LumoUtility.Padding.MEDIUM);
        div.getElement().getStyle().set("display", "flex");
        div.getElement().getStyle().set("align-items", "center");
        div.getElement().getStyle().set("gap", "var(--lumo-space-m)");
        return div;
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
}
