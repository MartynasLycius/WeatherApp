package com.proit.weather.view;

import com.proit.weather.service.SecurityService;
import com.proit.weather.utils.Messages;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import static com.proit.weather.utils.KEY_CONSTANTS.*;

@Route("login")
public class Login extends VerticalLayout implements BeforeEnterObserver, ComponentEventListener<AbstractLogin.LoginEvent> {
    private static final String LOGIN_SUCCESS_URL = "/";
    private static final String SIGNUP_URL = "/signup";
    private static final String ERROR_KEY = "error";
    private final LoginForm login = new LoginForm();

    public Login() {
        login.addLoginListener(this);

        Paragraph loginInstructions = new Paragraph(Messages.get(SIGNUP_TEXT));
        Anchor signUpLink = new Anchor(SIGNUP_URL, Messages.get(SIGNUP_LINK_TEXT));
        signUpLink.getElement().getStyle().set("margin-left", "5px");
        loginInstructions.add(signUpLink);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        add(new H1(Messages.get(APPLICATION_HEADER_KEY)), login, loginInstructions);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey(ERROR_KEY)) {
            login.setError(true);
        }
    }

    @Override
    public void onComponentEvent(AbstractLogin.LoginEvent loginEvent) {
        boolean authenticated = SecurityService.authenticate(loginEvent.getUsername(), loginEvent.getPassword());
        if (authenticated) {
            UI.getCurrent().getPage().setLocation(LOGIN_SUCCESS_URL);
        } else {
            login.setError(true);
        }
    }
}