package com.waheduzzaman.MeteoWeather.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.waheduzzaman.MeteoWeather.config.AppConfig;
import com.waheduzzaman.MeteoWeather.config.AppRoutes;
import com.waheduzzaman.MeteoWeather.security.UserDetailsServiceImpl;

public class MainLayout extends AppLayout implements BeforeEnterObserver {
    private final AppConfig appConfig;
    private final UserDetailsServiceImpl userDetailsService;
    private HorizontalLayout header;
    private final Button homeButton = new Button();

    public MainLayout(AppConfig appConfig, UserDetailsServiceImpl userDetailsService) {
        this.appConfig = appConfig;
        this.userDetailsService = userDetailsService;
        setClassName("bg-contrast-5");
        createHeader();
        renderHomeButton();
        getAuthButton();
    }

    private void createHeader() {
        H1 logoText = new H1(appConfig.getAPP_NAME());
        logoText.addClassNames("text-l", "m-m");

        header = new HorizontalLayout(new DrawerToggle(), logoText);
        header.getThemeList().set("dark", false);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        header.expand(logoText);

        header.setWidthFull();
        header.addClassNames("py-0", "px-m", "bg-base");

        setDrawerOpened(false);
        addToNavbar(header);
        setPrimarySection(Section.DRAWER);
    }

    private void getAuthButton() {
        if (!userDetailsService.isUserLoggedIn()) {
            Button loginButton = new Button("Login", event ->
                    UI.getCurrent().navigate(AppRoutes.LOGIN_ROUTE)
            );
            loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            header.add(loginButton);
        } else {
            Button logOutButton = new Button("Logout", event ->
                    userDetailsService.logout()
            );

            logOutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            logOutButton.getElement().getThemeList().add("badge primary");

            header.add(logOutButton);
        }
    }

    private void renderHomeButton() {
        homeButton.setClassName("home-button");
        homeButton.setIcon(VaadinIcon.HOME.create());
        homeButton.setText("Home");
        homeButton.addClickListener(event -> {
            AppRoutes.clearSessionValue("location");
            AppRoutes.openPage(AppRoutes.HOME_ROUTE);
            homeButton.setVisible(false);
        });
        header.add(homeButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        AppRoutes.setCurrentPath(event.getLocation().getPath());
        homeButton.setVisible(userDetailsService.isUserLoggedIn() && !AppRoutes.isSameRoute(AppRoutes.HOME_ROUTE, AppRoutes.getCurrentRoutePath()));
    }
}