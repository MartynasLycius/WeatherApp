package org.weather.app.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import org.weather.app.Utilities.JwtUtil;
import org.weather.app.Utilities.VaadinUtils;
import org.weather.app.components.NotificationComponent;
import org.weather.app.components.VerticalLayoutBuilder;
import org.weather.app.services.AuthService;

@Route(value = "login")
@PageTitle("Login")
public class LoginView extends AbstractView implements BeforeEnterObserver {

    @Inject
    private AuthService authService;

    @Inject
    private JwtUtil jwtUtil;

    private VerticalLayout contentLayout;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;
    private RouterLink registerLink;
    private H2 header;
    private boolean isLoggedIn = false;

    public LoginView() {

    }

    @Override
    public void initializeComponents() {
        getStyle().set("padding-top", "10%");
        header = new H2("Welcome");
        header.addClassName("welcome-text-style");

        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");

        loginButton = new Button("Login");
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loginButton.addClickListener(event -> handleLoginClick());

        registerLink = new RouterLink("Register", RegisterView.class);
        contentLayout = new VerticalLayoutBuilder().setWidth("40%").build();
    }

    @Override
    public void generateComponents() {
        contentLayout.add(
                header,
                usernameField,
                passwordField,
                loginButton,
                registerLink
        );
        add(contentLayout);
    }

    private void handleLoginClick() {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        try {
            authenticateUser(username, password);
        } catch (Exception e) {
            Notification.show("An unexpected error occurred: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

    private void authenticateUser(String username, String password) {
        authService.findByUsername(username).ifPresentOrElse(user -> {
            if (user.getPassword().equals(VaadinUtils.getMd5(password))) {
                String token = jwtUtil.generateToken(username);
                setCookies(token);
                NotificationComponent.createNotification("Login successful!", "success").open();
                HttpServletRequest request = VaadinUtils.getCurrentHttpRequest();
                request.setAttribute("user", user);
                UI.getCurrent().navigate(SearchLocationView.class);
            } else {
                NotificationComponent.createNotification("Invalid credentials", "failed").open();
            }
        }, () -> NotificationComponent.createNotification("User not found", "failed").open());
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        HttpServletRequest request = VaadinUtils.getCurrentHttpRequest();
        if (request != null && request.getAttribute("user") != null) {
            isLoggedIn = true;
        }
    }
}
