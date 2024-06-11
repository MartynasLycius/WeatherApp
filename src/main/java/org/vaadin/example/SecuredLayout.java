package org.vaadin.example;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import org.vaadin.example.service.SecurityService;
import org.vaadin.example.view.SecuredView;


public class SecuredLayout extends VerticalLayout implements RouterLayout, BeforeEnterObserver {

    @Inject
    private SecurityService securityService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SecuredView.class.isAssignableFrom(event.getNavigationTarget())) {
            if (!securityService.isAuthenticated()) {
                event.forwardTo("login");
            }
        }
    }
    public SecuredLayout() {
        HorizontalLayout topLayout = getHorizontalLayout();
        add(topLayout);
        addClassNames("centered-content", "w-100");
    }

    private HorizontalLayout getHorizontalLayout() {
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setWidthFull();
        topLayout.getStyle().set("border-bottom", "1px solid lightgrey");

        Button homeButton = new Button("Home");
        Button favoriteButton = new Button("Favorites");
        Button logoutButton = new Button("Logout");

        homeButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        favoriteButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate("favorites"));
        });
        logoutButton.addClickListener(event -> {
            securityService.logout();
            // Navigate to login page
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        H2 title = new H2("Weather App");
        topLayout.add( title, homeButton, favoriteButton, logoutButton);
        topLayout.expand(title);
        topLayout.setFlexGrow(1, title);
        topLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        return topLayout;
    }
}
