package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import jakarta.inject.Inject;
import org.vaadin.example.GuestLayout;
import org.vaadin.example.service.SecurityService;

@Route(value="login", layout = GuestLayout.class)
public class LoginView extends VerticalLayout implements GuestView{

    @Inject
    private SecurityService securityService;

    public LoginView() {
        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login");

        loginButton.addClickListener(event -> {
            String email = emailField.getValue();
            String password = passwordField.getValue();

            if (securityService.login(email, password)) {
                // Navigate to home
                getUI().ifPresent(ui -> ui.navigate(""));
            } else {
                add(new NativeLabel("Invalid email or password"));
            }
        });

        // For redirect to register view
        HorizontalLayout layoutForRegister = new HorizontalLayout();

        NativeLabel messageLabel = new NativeLabel("Don't have any account? ");

        Anchor registerLink = new Anchor("register","Register Now");
        registerLink.getStyle().set("color", "blue");
        registerLink.getElement().setAttribute("text-decoration", "underline");

        layoutForRegister.add(messageLabel, registerLink);

        add(emailField, passwordField, loginButton, layoutForRegister);
    }
}
