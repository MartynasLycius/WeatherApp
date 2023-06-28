package com.eastnetic.task.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    AuthenticationContext authContext;

    @Autowired
    public LoginView(AuthenticationContext authContext, @Value("${app.name}") String appName){
        this.authContext = authContext;
        setTitle(appName);
        setDescription("Built with ♥ by Vaadin");
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

        if (authContext.isAuthenticated()) {
            // Already logged in
            setOpened(false);
            beforeEnterEvent.forwardTo("");
        }
    }
}
