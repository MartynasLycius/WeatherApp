package com.proit.weather.service;

import com.proit.weather.model.User;
import com.proit.weather.utils.AES;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityService {
    public static final String USER_ATTRIBUTE = "SecurityService.User";
    private static final String LOGOUT_SUCCESS_URL = "/";
    private static final UserService userService = new UserService();
    public static boolean isAuthenticated() {
        return VaadinSession.getCurrent() != null && VaadinSession.getCurrent().getAttribute(USER_ATTRIBUTE) != null;
    }

    public static boolean authenticate(String username, String password) {
        VaadinServletRequest request = VaadinServletRequest.getCurrent();
        if (request == null) {
            return false;
        }
        User user = userService.findUserByUserName(username);
        if (user == null || !password.equals(AES.decrypt(user.getPassword()))) {
            return false;
        }

        VaadinSession.getCurrent().setAttribute(USER_ATTRIBUTE, user);
        request.getHttpServletRequest().changeSessionId();
        return true;
    }

    public static void logout() {
        UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
        VaadinSession.getCurrent().getSession().invalidate();
    }
}
