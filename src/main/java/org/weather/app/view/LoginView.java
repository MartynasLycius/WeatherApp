package org.weather.app.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.weather.app.config.Routes;

@PageTitle("Login")
@Route(Routes.LOGIN_ROUTE)
public class LoginView extends VerticalLayout implements BeforeEnterListener {

  private final LoginForm loginForm = new LoginForm();

  public LoginView() {
    Routes.setCurrentPath(Routes.LOGIN_ROUTE);
    addClassName("login-view");
    Span loginSpan = new Span();
    loginSpan.addClassNames(
        LumoUtility.Padding.LARGE,
        LumoUtility.Background.BASE,
        LumoUtility.BoxShadow.MEDIUM,
        LumoUtility.BorderRadius.LARGE);

    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);

    loginForm.setForgotPasswordButtonVisible(false);
    loginForm.setAction(Routes.LOGIN_ROUTE);

    loginSpan.add(new H1("Weather Forecast"), loginForm);
    add(loginSpan);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {}
}
