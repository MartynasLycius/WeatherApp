package org.weather.app.views;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;
import jakarta.servlet.http.Cookie;

public abstract class AbstractView  extends VerticalLayout{

    public AbstractView() {
        setSizeFull();
        setHeight("100vh"); // Set height to 100% of viewport height

        // Set the alignment and justification as needed
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        // Set the background image styles
        getStyle().set("background-image", "url('https://cdn.pixabay.com/photo/2016/01/16/01/00/blue-1142745_1280.jpg')");
        getStyle().set("background-size", "cover");
        getStyle().set("background-repeat", "no-repeat");
        getStyle().set("background-position", "center center");

        // Optional: Handle overflow and margin issues
        getStyle().set("margin", "0");
        getStyle().set("overflow", "hidden");

        // Ensure HTML and body also cover full height
        getStyle().set("height", "100%");
        getStyle().set("height", "100%");

        getStyle().set("overflow-y", "auto");
        getStyle().set("white-space", "nowrap");
        initializeComponents();
        generateComponents();
    }

    public abstract void generateComponents();
    public abstract void initializeComponents();

    public void setCookies(String token){
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        VaadinService.getCurrentResponse().addCookie(cookie);
    }

    public void removeCookies(){
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); // This deletes the cookie
        VaadinService.getCurrentResponse().addCookie(cookie);
    }
}
