package com.waheduzzaman.MeteoWeather.views.auth;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.waheduzzaman.MeteoWeather.config.AppRoutes;
import com.waheduzzaman.MeteoWeather.security.UserDetailsServiceImpl;

@PageTitle("Login | Meteo Weather")
@Route(AppRoutes.LOGIN_ROUTE)
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private final LoginForm login = new LoginForm();
    private final UserDetailsServiceImpl userDetailsService;

    public LoginView(UserDetailsServiceImpl userDetailsService) {
        AppRoutes.setCurrentPath(AppRoutes.LOGIN_ROUTE);
        this.userDetailsService = userDetailsService;
        addClassName("login-view");
        Span loginSpan = new Span();
        loginSpan.addClassNames(
                LumoUtility.Padding.LARGE,
                LumoUtility.Background.BASE,
                LumoUtility.BoxShadow.MEDIUM,
                LumoUtility.BorderRadius.LARGE
        );

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setForgotPasswordButtonVisible(false);
        login.setAction(AppRoutes.LOGIN_ROUTE);

        loginSpan.add(
                new H1("Meteo Weather"),
                login);
        add(loginSpan);
        ;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (userDetailsService.isUserLoggedIn()) {
            event.forwardTo("/");
        } else {
            login.setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
        }

//        if (event.getLocation()
//                .getQueryParameters()
//                .getParameters()
//                .containsKey("error")
//        ) {
//            login.setError(true);
//        }else{
//            event.forwardTo("/");
//        }
    }
}