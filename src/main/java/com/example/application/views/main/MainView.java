package com.example.application.views.main;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.backend.service.DailyWeatherService;
import com.example.application.backend.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.example.application.views.MarkWeatherView;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.example.application.views.WeatherView;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;

@PermitAll
public class MainView extends AppLayout {

	@Autowired
	UserService userService;

	@Autowired
	DailyWeatherService service;

	public MainView(DailyWeatherService service) {
		createHeader();
		this.service = service;
		service.weatherValueParse();

		createDrawer();
	}

	private void createHeader() {
		H1 logo = new H1("Weather Reports");
		logo.addClassNames("text-l", "m-m");

		Button logout = new Button("Log out", e -> userService.logout());
		HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);

		header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		header.expand(logo);
		header.setWidth("100%");
		header.addClassNames("py-0", "px-m");

		addToNavbar(header);

	}

	private void createDrawer() {
		RouterLink listLink = new RouterLink("Weather", WeatherView.class);
		listLink.setHighlightCondition(HighlightConditions.sameLocation());

		addToDrawer(new VerticalLayout(listLink, new RouterLink("Favourite Location", MarkWeatherView.class)));
	}
}
