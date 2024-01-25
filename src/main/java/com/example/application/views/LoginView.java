package com.example.application.views;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.grid.Grid;

import com.example.application.data.Users;

@Route("login") 
@PageTitle("Login | Vaadin CRM")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {
ComboBox<Users> company = new ComboBox<>("Company");
    Grid<Users> grid = new Grid<>(Users.class);

	private final LoginForm login = new LoginForm(); 

	public LoginView(){
		addClassName("login-view");
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");
		add(new Span("Username: user, Password: password"));
		add(login);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if(beforeEnterEvent.getLocation()  
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
}