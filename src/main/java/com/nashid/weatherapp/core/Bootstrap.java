package com.nashid.weatherapp.core;

import com.nashid.weatherapp.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

@Startup
@Singleton
public class Bootstrap implements ServletContextListener {

    @Inject
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @PostConstruct
    public void init() {
        userService.createImplementerLogin();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application is starting up...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application is shutting down...");
    }
}
