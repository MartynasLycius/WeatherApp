package com.nashid.weatherapp.views;

import com.nashid.weatherapp.core.notification.NotificationUtils;
import com.nashid.weatherapp.domain.User;
import com.nashid.weatherapp.services.UserService;
import com.vaadin.cdi.annotation.CdiComponent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.Route;

import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;

/**
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route("login")
@CdiComponent
public class LoginView extends Composite<LoginOverlay> {
    public final static String APPLICATION_NAME = "Weather Application";
    private final String APPLICATION_DESCRIPTION = "By Md Nashid Kamal";

    @Inject
    public LoginView(UserService userService) {
        LoginOverlay loginOverlay = getContent();
        loginOverlay.setTitle(APPLICATION_NAME);
        loginOverlay.setDescription(APPLICATION_DESCRIPTION);
        loginOverlay.setOpened(true);

        loginOverlay.addLoginListener(event -> {
            String username = event.getUsername();
            String password = event.getPassword();

            User user = userService.checkUser(username, password);
            if (user != null) {
                VaadinSession.getCurrent().setAttribute("LOGGED_IN_USER_ID", user.getId());
                UI.getCurrent().navigate("dashboard");
            }
            else {
                NotificationUtils.showErrorMessage("Username and password doesn't match");
                UI.getCurrent().getPage().setLocation("login");
            }
        });

        //todo - Implement Forgot Password If Needed
        loginOverlay.setForgotPasswordButtonVisible(false);
        loginOverlay.addForgotPasswordListener(event -> {

        });
    }
}
