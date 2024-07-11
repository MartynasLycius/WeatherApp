package com.proit.weather.service;

import com.proit.weather.view.Login;
import com.proit.weather.view.MainLayout;
import com.proit.weather.view.Registration;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.UIInitEvent;
import com.vaadin.flow.server.UIInitListener;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.auth.NavigationAccessControl;

public class NavigationAccessCheckerInitializer implements VaadinServiceInitListener, UIInitListener, BeforeEnterListener {
    public NavigationAccessCheckerInitializer() {
        NavigationAccessControl accessControl = new NavigationAccessControl();
        accessControl.setLoginView(Login.class);
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(this);
    }

    @Override
    public void uiInit(UIInitEvent uiInitEvent) {
        uiInitEvent.getUI().addBeforeEnterListener(this);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        boolean authenticated = SecurityService.isAuthenticated();

        if (beforeEnterEvent.getNavigationTarget().equals(Registration.class)) {
            return;
        }
        if (beforeEnterEvent.getNavigationTarget().equals(Login.class)) {
            if (authenticated) {
                beforeEnterEvent.forwardTo(MainLayout.class);
            }
            return;
        }

        if (!authenticated) {
            beforeEnterEvent.forwardTo(Login.class);
        }
    }
}