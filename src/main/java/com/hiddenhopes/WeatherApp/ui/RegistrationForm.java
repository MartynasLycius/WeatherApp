package com.hiddenhopes.WeatherApp.ui;

import com.hiddenhopes.WeatherApp.model.User;
import com.hiddenhopes.WeatherApp.repository.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Route("register")
@PageTitle("Register | Weather App")
@AnonymousAllowed
public class RegistrationForm extends VerticalLayout {
    TextField usernameField;
    PasswordField passwordField;
    Button registerButton;
    private final UserRepository userRepository;

    public RegistrationForm(UserRepository userRepository) {
        this.userRepository = userRepository;
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("WEATHER APP User Registration");

        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        usernameField.setRequired(true);
        usernameField.setPattern("^[a-zA-Z0-9]+$");
        passwordField.setRequired(true);
        // Add value change listeners to enable/disable the button
        usernameField.addValueChangeListener(event -> updateButtonState());
        passwordField.addValueChangeListener(event -> updateButtonState());

        registerButton = new Button("Register");
        registerButton.addClickListener(event -> registerUser(usernameField.getValue(), passwordField.getValue()));

        add(title, usernameField, passwordField, registerButton);
    }

    private void updateButtonState() {
        boolean fieldsNotEmpty = !usernameField.isEmpty() && !passwordField.isEmpty();
        registerButton.setEnabled(fieldsNotEmpty);
    }

    private void registerUser(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = new User(username, encoder.encode(password));
        userRepository.save(user);
        Notification.show("User registered successfully!", 2000, Notification.Position.BOTTOM_CENTER);
        getUI().ifPresent(e -> e.navigate(LoginView.class));
    }
}
