package com.eastnetic.task.views.login;

import com.eastnetic.task.service.UsersService;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    @Autowired
    UsersService usersService;

    @Autowired
    public LoginView(@Value("${app.name}") String appName){
        setTitle(appName);
        setDescription("Get Weather Forecast in a Minute");
        setForgotPasswordButtonVisible(false);
        setOpened(true);
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            setError(true);
        }

        if (usersService.isUserLoggedIn()) {
            // Already logged in
            setOpened(false);
            beforeEnterEvent.forwardTo("");
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        usersService.addUser("admin", "admin", "ADMIN", "John Doe Admin");
        usersService.addUser("user", "user", "USER", "Jane Doe User");
        super.onAttach(attachEvent);
    }
}
