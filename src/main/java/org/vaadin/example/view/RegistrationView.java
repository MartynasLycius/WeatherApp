package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;
import org.vaadin.example.GuestLayout;
import org.vaadin.example.entity.User;
import org.vaadin.example.service.SecurityService;
import org.vaadin.example.service.UserService;

@Route(value="register", layout = GuestLayout.class)
public class RegistrationView extends VerticalLayout implements GuestView{

    @Inject
    private SecurityService securityService;
    @Inject
    private UserService userService;

    public RegistrationView() {
        TextField nameField = new TextField("Name");
        EmailField emailField = new EmailField("Email");
        PasswordField passwordField = new PasswordField("Password");
        Button registerButton = new Button("Register");

        registerButton.addClickListener(event -> {
            User user = new User();
            user.setName(nameField.getValue());
            user.setEmail(emailField.getValue());
            user.setPassword(passwordField.getValue());
            userService.register(user);

            securityService.login(emailField.getValue(), passwordField.getValue());
            // Navigate to home
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        // For redirect to login view
        HorizontalLayout layoutForLogin = new HorizontalLayout();

        NativeLabel messageLabel = new NativeLabel("Already have any account? ");

        Anchor loginLink = new Anchor("login","Login Now");
        loginLink.getStyle().set("color", "blue");
        loginLink.getElement().setAttribute("text-decoration", "underline");

        layoutForLogin.add(messageLabel, loginLink);


        add(nameField, emailField, passwordField, registerButton, layoutForLogin);
    }
}
