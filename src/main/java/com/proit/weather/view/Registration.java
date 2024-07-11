package com.proit.weather.view;

import com.proit.weather.enums.RoleType;
import com.proit.weather.model.User;
import com.proit.weather.service.UserService;
import com.proit.weather.utils.Messages;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyDownEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.internal.KeyboardEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

import java.util.Locale;

import static com.proit.weather.utils.KEY_CONSTANTS.*;
import static com.vaadin.flow.component.Key.ENTER;

@Route("/signup")
public class Registration extends HorizontalLayout {
    private static final String LOGIN_URL = "/login";
    private static final String USER_SUCCESS_MSG = "User saved: %s, %s";
    private static final String USER_ERROR_MSG = "User could not be saved, %s";
    private final UserService userService = new UserService();
    private final Binder<User> binder;
    private final User user;

    public Registration() {
        Messages.setLocale(Locale.ENGLISH);
        user = new User();

        EmailField emailField = new EmailField(Messages.get(EMAIL_TITLE));
        TextField usernameField = new TextField(Messages.get(USERNAME_TITLE));
        TextField passwordField = new TextField(Messages.get(PASSWORD_TITLE));
        emailField.addKeyDownListener(ENTER, (ComponentEventListener<KeyDownEvent>) this::submit);
        usernameField.addKeyDownListener(ENTER, (ComponentEventListener<KeyDownEvent>) this::submit);
        passwordField.addKeyDownListener(ENTER, (ComponentEventListener<KeyDownEvent>) this::submit);

        binder = new Binder<>(User.class);
        binder.forField(usernameField).
                asRequired(Messages.get(USERNAME_ERROR_KEY))
                .withValidator(Validator.from(this::existingUser, Messages.get(DUPLICATE_USERNAME_ERROR_KEY)))
                .bind(User::getUsername, User::setUsername);
        binder.forField(passwordField).asRequired(Messages.get(PASSWORD_ERROR_KEY)).bind(User::getPassword, User::setEncryptedPassword);
        binder.forField(emailField)
                .asRequired(Messages.get(EMAIL_ERROR_KEY))
                .withValidator(Validator.from(email -> email.contains("@"), Messages.get(EMAIL_PARTS_ERROR_KEY)))
                .bind(User::getEmail, User::setEmail);

        Button submitButton = new Button(Messages.get(REGISTER_BUTTON_TEXT), event -> addUser());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.getStyle().setMarginTop("20px");

        Paragraph loginInstructions = new Paragraph(Messages.get(HAVE_ACCOUNT));
        Anchor loginLink = new Anchor(LOGIN_URL, Messages.get(LOGIN_LINK_TEXT));
        loginLink.getElement().getStyle().set("margin-left", "5px");
        loginInstructions.add(loginLink);

        H1 header = new H1(Messages.get(APPLICATION_HEADER_KEY));
        header.getStyle().setMargin("50px");
        header.getStyle().setTextAlign(Style.TextAlign.CENTER);
        H3 title = new H3(Messages.get(FORM_HEADER_KEY));

        FormLayout form = new FormLayout();
        form.setWidth("350px");
        form.add(header, title, emailField, usernameField, passwordField, submitButton, loginInstructions);

        setAlignItems(FlexComponent.Alignment.STRETCH);
        setJustifyContentMode(JustifyContentMode.CENTER);
        add(form);
    }

    private void submit(KeyboardEvent event) {
        if (Key.ENTER.equals(event.getKey())) {
            addUser();
        }
    }

    private void addUser() {
        try {
            binder.writeBean(user);
            user.setRoleType(RoleType.ADMIN);
            userService.createUser(user);
            Notification.show(String.format(USER_SUCCESS_MSG, user.getUsername(), user.getEmail()));
            UI.getCurrent().navigate(Login.class);
        } catch (ValidationException e) {
            Notification.show(String.format(USER_ERROR_MSG, e.getMessage()));
        }
    }

    private boolean existingUser(String userName) {
        return userService.findUserByUserName(userName) == null;
    }
}
