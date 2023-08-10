package org.weather.app.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import org.weather.app.config.Constants;
import org.weather.app.config.Routes;
import org.weather.app.config.WeatherConfig;
import org.weather.app.service.DomainUserDetailsService;

public class ApplicationLayout extends AppLayout implements BeforeEnterObserver {

  private final WeatherConfig weatherConfig;
  private final DomainUserDetailsService domainUserDetailsService;
  private HorizontalLayout header;
  private final Button profile = new Button();
  private final Button defaultButton = new Button();

  public ApplicationLayout(
      WeatherConfig weatherConfig, DomainUserDetailsService domainUserDetailsService) {
    this.weatherConfig = weatherConfig;
    this.domainUserDetailsService = domainUserDetailsService;
    setClassName("bg-contrast-5");
    buildHeaderSection();
    buildAuthenticationButtons();
    buildProfileButton();
    buildHomeButton();
  }

  private void buildHeaderSection() {
    setPrimarySection(Section.DRAWER);
    H1 appName = new H1(weatherConfig.getAppName());
    appName.addClassNames("text-xl", "m-m");
    Image image = buildIcon();
    header = new HorizontalLayout(new DrawerToggle(), image, appName);
    header.getThemeList().set("dark", false);
    header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    header.expand(appName);
    header.setWidthFull();
    header.addClassNames("py-0", "px-m", "bg-base");
    setDrawerOpened(false);
    addToNavbar(header);
  }

  private Image buildIcon() {
    Image image = new Image(Constants.APP_ICON, "app icon");
    image.setWidth("45px");
    image.setHeight("50px");
    image.getStyle().set("margin-right", "-25px");
    return image;
  }

  private void buildAuthenticationButtons() {
    if (domainUserDetailsService.isUserLoggedIn()) {
      Button logout = new Button("", event -> domainUserDetailsService.logout());
      applyCommonButton(logout, "Logout", VaadinIcon.SIGN_OUT.create());
      logout.addThemeVariants(ButtonVariant.LUMO_ERROR);
      logout.getElement().getThemeList().add("badge primary");
      header.add(logout);
      return;
    }
    Button login = new Button("", event -> UI.getCurrent().navigate(Routes.LOGIN_ROUTE));
    applyCommonButton(login, "Login", VaadinIcon.SIGN_IN.create());
    login.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    header.add(login);
  }

  private void buildProfileButton() {
    applyCommonButton(profile, "Profile", VaadinIcon.USER.create());
    profile.addClickListener(
        event -> {
          Routes.clearSessionValue(Constants.SESSION_KEY);
          Routes.openPage(Routes.PROFILE_ROUTE);
          profile.setVisible(false);
        });
    header.add(profile);
  }

  private void buildHomeButton() {
    applyCommonButton(defaultButton, "Home", VaadinIcon.HOME.create());
    defaultButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    defaultButton.getElement().getThemeList().add("badge primary");
    defaultButton.addClickListener(
        event -> {
          Routes.openPage(Routes.DEFAULT_ROUTE);
          defaultButton.setVisible(false);
        });
    header.add(defaultButton);
  }

  private void applyCommonButton(Button button, String text, Icon icon) {
    button.setText(text);
    button.setIcon(icon);
    button.setClassName("common-button");
  }

  @Override
  public void beforeEnter(BeforeEnterEvent event) {
    String currentPath = event.getLocation().getPath();
    Routes.setCurrentPath(currentPath);
    profile.setVisible(false);
    defaultButton.setVisible(false);
    if (domainUserDetailsService.isUserLoggedIn()) {
      if (currentPath.equalsIgnoreCase("")) {
        profile.setVisible(true);
      }
      if (currentPath.equalsIgnoreCase("profile")) {
        defaultButton.setVisible(true);
      }
    }
  }
}
