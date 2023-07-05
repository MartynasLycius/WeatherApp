package com.eastnetic.task.views.layout;

import com.eastnetic.task.service.UsersService;
import com.eastnetic.task.views.pages.DashboardView;
import com.eastnetic.task.views.pages.LocationView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


public class MainLayout extends AppLayout {

    private UsersService usersService;
    private final AccessAnnotationChecker accessChecker;
    private String appName;

    /**
     * Main layout for all views
     * @param usersService, accessChecker
     * @return
     * @throws
     */
    @Autowired
    public MainLayout(UsersService usersService, AccessAnnotationChecker accessChecker, @Value("${app.name}") String appName) {
        this.usersService = usersService;
        this.accessChecker = accessChecker;
        this.appName = appName;

        createHeader();
        createNavBar();
    }

    /**
     * Create header layout
     * @param
     * @return
     * @throws
     */
    private void createHeader() {
        H3 appTitle = new H3(appName);
        appTitle.addClassName("logo");
        HorizontalLayout titleLayout = new HorizontalLayout(new Icon(VaadinIcon.CLOUD_O), appTitle);

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), titleLayout, userMenu());
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");

        addToNavbar(header);
    }

    /**
     * Create user menu in header layout
     * @param
     * @return
     * @throws
     */
    private Div userMenu() {
        Div layout = new Div();

        if (usersService.isUserLoggedIn()) {
            MenuBar userMenu = new MenuBar();
            HorizontalLayout userMenuLayout = new HorizontalLayout();
            userMenuLayout.add(new Icon(VaadinIcon.USER_CHECK));
            userMenuLayout.add(this.usersService.getCurrentUser().getName());
            userMenuLayout.add(new Icon("lumo", "dropdown"));
            MenuItem userMenuItem = userMenu.addItem(userMenuLayout);
            userMenuItem.getSubMenu().addItem("Log out", e -> logout());
            layout.add(userMenu);
            layout.getElement().getStyle().set("margin-left", "auto");
        } else {
            Anchor loginLink = new Anchor("login", "Log in");
            layout.add(loginLink);
        }
        return layout;
    }

    /**
     * Create logout layout
     * @param
     * @return
     * @throws
     */
    private void logout(){
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Logout");
        dialog.setText(
                "Are you sure you want to logout?");
        dialog.setCancelable(true);
        dialog.setCancelText("No");
        dialog.setConfirmText("Yes");
        dialog.addConfirmListener(event -> this.usersService.logout());
        dialog.open();
    }

    /**
     * Create navigation sidebar
     * @param
     * @return
     * @throws
     */
    private void createNavBar(){
        Tabs tabs = new Tabs();
        if (accessChecker.hasAccess(DashboardView.class)) {
            tabs.add(createTab(VaadinIcon.DASHBOARD, "Dashboard", DashboardView.class));
        }
        if (accessChecker.hasAccess(LocationView.class)) {
            tabs.add(createTab(VaadinIcon.SEARCH_PLUS, "Weather Forecast", LocationView.class));
        }
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
    }

    /**
     * Create tabs for navbar
     * @param
     * @return
     * @throws
     */
    private Tab createTab(VaadinIcon viewIcon, String viewName, Class routeView) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        link.setRoute(routeView);
        link.setTabIndex(-1);

        return new Tab(link);
    }
}

