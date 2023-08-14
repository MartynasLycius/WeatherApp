package com.weather.application.util;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;
import com.weather.application.exception.CustomErrorHandler;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addSessionInitListener(
                initEvent -> {
                    VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
                    LoggerFactory.getLogger(getClass()).info("A new Session has been initialized!");
                });

        event.getSource().addUIInitListener(
                initEvent -> {
                    VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
                    LoggerFactory.getLogger(getClass()).info("A new UI has been initialized!");
                });
    }
}
