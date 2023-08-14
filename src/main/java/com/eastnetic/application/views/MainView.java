package com.eastnetic.application.views;

import com.eastnetic.application.authentication.AuthService;
import com.eastnetic.application.users.dto.UserDto;
import com.eastnetic.application.users.exceptions.UserException;
import com.eastnetic.application.users.service.UserService;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Route("login")
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Login")
public class MainView extends VerticalLayout {

    private static final Logger LOGGER = LogManager.getLogger(MainView.class);

    private final AuthService authService;
    private final UserService userService;

    private final TextField loginUsername = new TextField("Username");
    private final PasswordField loginPassword = new PasswordField("Password");

    private final TextField regUsername = new TextField("Username");
    private final PasswordField regPassword = new PasswordField("Password");

    Tabs tabs = new Tabs();
    Tab loginTab = new Tab("Login");
    Tab registrationTab = new Tab("Registration");

    public MainView(AuthService authService, UserService userService) {

        this.authService = authService;
        this.userService = userService;

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

        try {

            UserDto userDto = new UserDto(username, password);

            userService.registerUser(userDto);
            tabs.setSelectedTab(loginTab);

            Notification.show("Registration success.", 1000, Notification.Position.TOP_CENTER);

        } catch (UserException e) {

            LOGGER.error("User registration failed: Username={}", username, e);

            Notification.show("Registration failed." + e.getMessage(), 3000, Notification.Position.TOP_CENTER);

        } catch (Exception e) {

            LOGGER.error("User registration failed: Username={}", username, e);

            Notification.show("Registration failed. Please try again later.", 3000, Notification.Position.TOP_CENTER);
        }
    }

    private void login() {

        String username = loginUsername.getValue();
        String password = loginPassword.getValue();

        try {

            boolean loginSuccessful = authService.isAuthenticatedUser(username, password);

            if (loginSuccessful) {

                VaadinSession.getCurrent().setAttribute("username", username);

                Notification.show("Login successful.", 1000, Notification.Position.TOP_CENTER);

                UI.getCurrent().navigate(LocationSearchView.class);

            } else {
                Notification.show("Login failed: Invalid credentials.", 3000, Notification.Position.TOP_CENTER);
            }

        } catch (UsernameNotFoundException e) {

            Notification.show("Login failed: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);

        } catch (Exception e) {

            LOGGER.error("User login failed: Username={}", username, e);

            Notification.show("Login failed. Please try again later.", 3000, Notification.Position.TOP_CENTER);
        }
    }
}

