package com.sheikhimtiaz.application.view;

import com.sheikhimtiaz.application.entity.User;
import com.sheikhimtiaz.application.model.Role;
import com.sheikhimtiaz.application.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Route("signup")
@AnonymousAllowed
public class SignUpView extends VerticalLayout {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpView(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;

        TextField username = new TextField("Username", "johndoe", "Username");
        TextField name = new TextField("Full Name","John Doe","Full Name");
        TextField role = new TextField("Role", "admin", "Role");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm Password");

        Button signUpButton = new Button("Sign Up", event -> {
            if (!password.getValue().equals(confirmPassword.getValue())) {
                Notification.show("Passwords do not match");
                return;
            }

            User newUser = new User();
            newUser.setVersion(1);
            newUser.setUsername(username.getValue());
            newUser.setName(name.getValue());
            newUser.setHashedPassword(passwordEncoder.encode(password.getValue()));
            newUser.setRoles(Set.of(Role.valueOf(role.getValue().toUpperCase())));

            userService.update(newUser);

            Notification.show("Registration successful!");
            UI.getCurrent().navigate("login");
        });

        Button loginButton = new Button("Log in", event -> {
            UI.getCurrent().navigate("login");
        });

        VerticalLayout formLayout = new VerticalLayout(username, name, role, password, confirmPassword, signUpButton, loginButton);
        formLayout.setPadding(true);
        formLayout.setSpacing(true);
        formLayout.setSizeUndefined();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setSizeFull();
        add(new H2("Create Account"), formLayout);
        setClassName("signup-view");
        getElement().getStyle().set("background-color", "#f4f4f4");
        formLayout.getElement().getStyle().set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("padding", "20px")
                .set("background-color", "#fff");

//        add(new H2("Create Account"), username, name, role, password, confirmPassword, signUpButton, loginButton);
//        setAlignItems(Alignment.CENTER);
//        setJustifyContentMode(JustifyContentMode.CENTER);
//        setSizeFull();
    }
}
