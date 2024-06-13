package com.weatherapp.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.weatherapp.model.User;
import com.weatherapp.services.UserService;
import com.weatherapp.services.UserServiceImpl;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */
@AnonymousAllowed
@Route("register")
public class RegisterView extends Composite {

    private final UserService userService;

    public RegisterView(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected Component initContent() {
        TextField username = new TextField("User Name");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm password");
        VerticalLayout registerForm =  new VerticalLayout(
                new H2("Register"),
                username,
                password1,
                password2,
                new HorizontalLayout(
                        new Button("Register", event -> register(
                                username.getValue(),
                                password1.getValue(),
                                password2.getValue()
                        )),
                        new Button("Login", buttonClickEvent -> {
                            UI.getCurrent().navigate("login");})
                        )
        );
        registerForm.setSizeFull();
        registerForm.setAlignItems(FlexComponent.Alignment.CENTER);
        return registerForm;
    }

    private void register(String username, String password1, String password2) {
        if (username.trim().isEmpty()) {
            Notification.show("Enter a username");
        } else if (password1.isEmpty()) {
            Notification.show("Enter a password");
        } else if (!password1.equals(password2)) {
            Notification.show("Passwords don't match");
        } else {
            User user = userService.getUser(username);
            if(user != null) {
                Notification.show("User already exists");
                return;
            }
            else {
                userService.register(username, password1);
                Notification.show("Registered successfully, Go to login page to login.");
            }
        }
    }
}
