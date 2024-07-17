package org.weather.app.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;
import org.weather.app.Utilities.VaadinUtils;
import org.weather.app.components.NotificationComponent;
import org.weather.app.components.VerticalLayoutBuilder;
import org.weather.app.entities.User;
import org.weather.app.services.AuthService;

import java.util.UUID;

@Route("register")
public class RegisterView extends AbstractView {

    @Inject
    private AuthService authService;

    private VerticalLayout contentLayout;
    private TextField usernameTextField;
    private PasswordField passwordTextField;
    private PasswordField confirmPasswordTextField;
    private Button registerButton;
    private H2 header;

    public RegisterView() {

    }

    @Override
    public void initializeComponents() {
        getStyle().set("padding-top", "10%");
        header = new H2("Register");
        header.addClassName("welcome-text-style");

        usernameTextField = new TextField("Username");
        passwordTextField = new PasswordField("Password");
        confirmPasswordTextField = new PasswordField("Re-Password");

        registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(event -> handleRegisterClick());
        contentLayout = new VerticalLayoutBuilder().setWidth("40%").build();
    }

    @Override
    public void generateComponents() {
        contentLayout.add(
                header,
                usernameTextField,
                passwordTextField,
                confirmPasswordTextField,
                registerButton
        );
        add(contentLayout);
    }

    private void handleRegisterClick() {
        String username = usernameTextField.getValue();
        String password = passwordTextField.getValue();
        String confirmPassword = confirmPasswordTextField.getValue();

        try {
            register(username, password, confirmPassword);
        } catch (Exception e) {
            NotificationComponent.createNotification( "An unexpected error occurred: " + e.getMessage(), "failed").open();
        }
    }

    private void register(String username, String password, String confirmPassword) throws Exception {
        if (isInputValid(username, password, confirmPassword)) {
            if (authService.findByUsername(username).isPresent()) {
                NotificationComponent.createNotification("Username already taken", "failed").open();
            } else {
                createUserAndSave(username, password);
            }
        }
    }

    private boolean isInputValid(String username, String password, String confirmPassword) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            NotificationComponent.createNotification("Username and password cannot be empty", "failed").open();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            NotificationComponent.createNotification("Passwords do not match", "failed").open();
            return false;
        }

        return true;
    }

    private void createUserAndSave(String username, String password) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(username);
        user.setPassword(VaadinUtils.getMd5(password));
        authService.save(user);
        NotificationComponent.createNotification( "Registration successful!", "success").open();
        getUI().ifPresent(ui -> ui.navigate("login"));
    }
}

