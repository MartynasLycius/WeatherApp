package org.vaadin.example.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.inject.Inject;
import org.vaadin.example.MainView;
import org.vaadin.example.model.Users;
import org.vaadin.example.service.UserService;

import java.util.Optional;

@Route("/WeatherApp/login")
public class LoginView extends VerticalLayout {
    @Inject
    private UserService userService;

    // Public no-arg constructor
    public LoginView() {
        initLoginView();
    }
    private void initLoginView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        createHeader();

        VerticalLayout loginFormLayout = createLoginForm();
        loginFormLayout.setSizeUndefined();
        loginFormLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        loginFormLayout.getStyle().set("border", "1px solid var(--lumo-contrast-10pct)");
        loginFormLayout.getStyle().set("border-radius", "8px");
        loginFormLayout.getStyle().set("padding", "var(--lumo-space-m)");
        loginFormLayout.getStyle().set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.1)");
        loginFormLayout.setWidth("300px");

        VerticalLayout centeredLayout = new VerticalLayout(loginFormLayout);
        centeredLayout.setSizeFull();
        centeredLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        centeredLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        add(centeredLayout);
    }

    private void createHeader() {
        H3 title = new H3("Weather App");
        title.getStyle().set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.setSpacing(true);
        header.getStyle().set("padding", "var(--lumo-space-m)")
                .set("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.1)");

        add(header);
    }

    private VerticalLayout createLoginForm() {
        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");

        Button loginButton = new Button("Login", e -> {
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            Optional<Users> user = userService.findByUsernameAndPassword(username, password);
            if (user.isPresent()) {
                VaadinSession.getCurrent().setAttribute("id", user.get().getId());
                VaadinSession.getCurrent().setAttribute("username", user.get().getUsername());
                VaadinSession.getCurrent().setAttribute("fullName", user.get().getFullName());
                getUI().ifPresent(ui -> ui.navigate(MainView.class));
            } else {
                Span errorMessage = new Span("Invalid username or password");
                add(errorMessage);
            }
        });
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button backButton = new Button("Back to Main Page", e -> {
            getUI().ifPresent(ui -> ui.navigate(MainView.class));
        });
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        VerticalLayout loginFormLayout = new VerticalLayout(usernameField, passwordField, loginButton, backButton);
        loginFormLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        loginFormLayout.setSpacing(true);

        return loginFormLayout;
    }
}
