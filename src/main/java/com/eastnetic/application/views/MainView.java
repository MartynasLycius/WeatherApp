package com.eastnetic.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Login")
public class MainView extends VerticalLayout {

    private final TextField loginUsername = new TextField("Username");
    private final PasswordField loginPassword = new PasswordField("Password");

    private final TextField regUsername = new TextField("Username");
    private final PasswordField regPassword = new PasswordField("Password");

    Tabs tabs = new Tabs();
    Tab loginTab = new Tab("Login");
    Tab registrationTab = new Tab("Registration");

    public MainView() {

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        HorizontalLayout loginLayout = new HorizontalLayout(configureLoginForm());
        HorizontalLayout registrationLayout = new HorizontalLayout(configureRegistrationForm());

        tabs.add(loginTab, registrationTab);

        tabs.addSelectedChangeListener(event -> {

            if (event.getSelectedTab() == loginTab) {
                removeAll();
                add(tabs, loginLayout);

            } else if (event.getSelectedTab() == registrationTab) {
                removeAll();
                add(tabs, registrationLayout);
            }
        });

        add(tabs, loginLayout);
    }

    private FormLayout configureLoginForm() {

        Button loginButton = new Button("Login", event -> login());

        return new FormLayout(loginUsername, loginPassword, loginButton);
    }

    private FormLayout configureRegistrationForm() {

        Button registerButton = new Button("Register", event -> register());

        return new FormLayout(regUsername, regPassword, registerButton);
    }

    private void register() {

        String username = regUsername.getValue();
        String password = regPassword.getValue();

        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            return;
        }

        tabs.setSelectedTab(loginTab);

        Notification.show("Registration success.", 1000, Notification.Position.TOP_CENTER);
    }

    private void login() {

        String username = loginUsername.getValue();
        String password = loginPassword.getValue();

        boolean loginSuccessful = true; //Todo call login service.

        if (loginSuccessful) {

            VaadinSession.getCurrent().setAttribute("username", username);

            Notification.show("Login successful.", 1000, Notification.Position.TOP_CENTER);

            UI.getCurrent().navigate(LocationSearchView.class);

        } else {
            Notification.show("Login failed: Invalid credentials.", 3000, Notification.Position.TOP_CENTER);
        }
    }
}

