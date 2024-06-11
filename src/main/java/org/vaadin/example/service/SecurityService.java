package org.vaadin.example.service;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.vaadin.example.entity.User;

import java.io.Serializable;
import java.util.Optional;

@SessionScoped
public class SecurityService implements Serializable {

    @Inject
    private UserService userService;

    @Inject
    private HttpServletRequest httpServletRequest;

    public boolean login(String email, String password) {
        Optional<User> user = userService.login(email, password);
        if (user.isPresent()) {
            httpServletRequest.getSession().setAttribute("user", user.get());
            return true;
        }
        return false;
    }

    public void logout() {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public boolean isAuthenticated() {
        HttpSession session = httpServletRequest.getSession(false);
        return session != null && session.getAttribute("user") != null;
    }

    public User getAuthenticatedUser() {
        HttpSession session = httpServletRequest.getSession(false);
        return session != null ? (User) session.getAttribute("user") : null;
    }

}
