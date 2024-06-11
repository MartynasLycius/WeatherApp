package org.vaadin.example;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import jakarta.inject.Inject;
import org.vaadin.example.service.SecurityService;
import org.vaadin.example.view.GuestView;
import org.vaadin.example.view.SecuredView;

public class GuestLayout extends VerticalLayout implements RouterLayout, BeforeEnterObserver {
    @Inject
    private SecurityService securityService;

    public GuestLayout() {
        H2 title = new H2("Weather App");
        title.getStyle().setBorderBottom("1px solid lightgrey");
        title.setWidthFull();
        title.getStyle().setPaddingBottom("10px");
        add(title);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (GuestView.class.isAssignableFrom(event.getNavigationTarget())) {
            if (securityService.isAuthenticated()) {
                event.forwardTo("");
            }
        }
    }
}
