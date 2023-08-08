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

    /**
     * Main login view layout create
     * @param appName from properties
     * @return
     * @throws
     */
    @Autowired
    public LoginView(@Value("${app.name}") String appName){
        setTitle(appName);
        setDescription("Get Weather Forecast in a Minute");
        setForgotPasswordButtonVisible(false);
        setOpened(true);
        setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));
    }

    /**
     * Before enter event action to check if user is already logged in or any error occurred
     * @param beforeEnterEvent
     * @return
     * @throws
     */
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

    /**
     * Attach event to create default users on load
     * @param attachEvent
     * @return
     * @throws
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        usersService.addUser("admin", "admin", "ADMIN", "John Doe Admin");
        usersService.addUser("user", "user", "USER", "Jane Doe User");
        super.onAttach(attachEvent);
    }
}
