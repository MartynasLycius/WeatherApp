package com.example.application.views.login;

import com.example.application.service.UserService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private final UserService userService;

    public LoginView(UserService userService) {
        this.userService = userService;
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Weather App");
        i18n.getHeader().setDescription("Enter username and password");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (userService.isUserLoggedIn()) {
            // Already logged in
            setOpened(false);
            event.forwardTo("search");
        }

        setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        userService.addUser(1L, "user1", "password");
        userService.addUser(2L, "user2", "password");
        super.onAttach(attachEvent);
    }
}
