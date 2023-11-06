package com.weather.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.logging.Logger;

@Route("login")
@PageTitle("Login | WeatherApp")
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private static final Logger LOGGER = Logger.getLogger(LoginView.class.getName());

    private LoginForm login = new LoginForm();

    public LoginView() {
        LOGGER.info("Initiate Login View");
        Image logoImage = new Image("images/logo.png", "Weather App");
        logoImage.setWidth("100px");
        logoImage.setHeight("100px");

        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");

        add(
                logoImage,
                new H1("Weather App"),
                login
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")
        ){
            login.setError(true);
        }
    }
}
