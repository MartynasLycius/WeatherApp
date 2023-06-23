package com.hiddenhopes.WeatherApp.ui;

import com.hiddenhopes.WeatherApp.model.User;
import com.hiddenhopes.WeatherApp.repository.UserRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    private final UserRepository userRepository;

    @Autowired
    public LoginView(UserRepository userRepository) {
        this.userRepository = userRepository;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        // loginForm.addLoginListener(e -> authenticate(loginForm, e));

        add(new H1("Weather App"), loginForm);
    }

    private void authenticate(LoginForm loginForm, LoginForm.LoginEvent event) {
        String username = event.getUsername();
        String password = event.getPassword();

        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            loginForm.setError(false);
            loginForm.setEnabled(false);
            getUI().ifPresent(ui -> ui.navigate(GeocodingUI.class));
        } else {
            loginForm.setError(true);
        }
    }
}

