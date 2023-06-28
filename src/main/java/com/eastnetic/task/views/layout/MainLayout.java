package com.eastnetic.task.views.layout;

import com.eastnetic.task.views.pages.DashboardView;
import com.eastnetic.task.views.pages.LocationView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
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
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.ZoneId;
import java.util.TimeZone;

public class MainLayout extends AppLayout {
    private final transient AuthenticationContext authContext;
    private final AccessAnnotationChecker accessChecker;
    private String appName;

    @Autowired
    public MainLayout(AuthenticationContext authContext, AccessAnnotationChecker accessChecker, @Value("${app.name}") String appName) {
        this.authContext = authContext;
        this.accessChecker = accessChecker;
        this.appName = appName;

        createHeader();
        createNavBar();
    }

    /**
     *
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

    private Div userMenu() {
        Div layout = new Div();

        if (authContext.isAuthenticated()) {
            MenuBar userMenu = new MenuBar();
            HorizontalLayout userMenuLayout = new HorizontalLayout();
            userMenuLayout.add(new Icon(VaadinIcon.USER_CHECK));
            userMenuLayout.add(this.authContext.getPrincipalName().get());
            userMenuLayout.add(new Icon("lumo", "dropdown"));
            MenuItem userMenuItem = userMenu.addItem(userMenuLayout);
            userMenuItem.getSubMenu().addItem("Log out", e -> this.authContext.logout());
            layout.add(userMenu);
            layout.getElement().getStyle().set("margin-left", "auto");
        } else {
            Anchor loginLink = new Anchor("login", "Log in");
            layout.add(loginLink);
        }
        return layout;
    }

    private void createNavBar(){
        Tabs tabs = new Tabs();
        if (accessChecker.hasAccess(DashboardView.class)) {
            tabs.add(createTab(VaadinIcon.DASHBOARD, "Dashboard", DashboardView.class));
        }
        if (accessChecker.hasAccess(LocationView.class)) {
            tabs.add(createTab(VaadinIcon.SEARCH_PLUS, "Find Location", LocationView.class));
        }
        if (accessChecker.hasAccess(MainLayout.class)) {
            tabs.add(createTab(VaadinIcon.CHART, "Analytics", MainLayout.class));
        }
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        addToDrawer(tabs);
    }


    private Tab createTab(VaadinIcon viewIcon, String viewName, Class routeView) {
        Icon icon = viewIcon.create();
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span(viewName));
        // Demo has no routes
        link.setRoute(routeView);
        link.setTabIndex(-1);

        return new Tab(link);
    }



        /*@Override
        protected void afterNavigation() {
            super.afterNavigation();
            viewTitle.setText(getCurrentPageTitle());
        }

        private String getCurrentPageTitle() {
            PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
            return title == null ? "" : title.value();
        }*/

       /* @Override
        protected void onAttach(AttachEvent attachEvent) {
            UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> {
                TimeZone uiTimeZone = TimeZone.getTimeZone(details.getTimeZoneId());
                VaadinSession session = VaadinSession.getCurrent();
                session.setAttribute(ZoneId.class, uiTimeZone.toZoneId());
            });
        }*/
    }

