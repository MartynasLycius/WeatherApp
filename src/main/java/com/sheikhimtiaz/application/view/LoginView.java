package com.sheikhimtiaz.application.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends LoginOverlay {

    public LoginView() {
        setAction("login");

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setDescription("Login using admin/admin or sign up from /signup page.");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);

        Button signUpButton = new Button("Sign up", event -> {
            UI.getCurrent().navigate("signup");
        });

        VerticalLayout layout = new VerticalLayout(signUpButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        getElement().appendChild(layout.getElement());

        setOpened(true);
    }
}
