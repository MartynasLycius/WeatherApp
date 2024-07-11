package com.weather.application.exception;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;
@Component
public class CustomErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(CustomErrorHandler.class.getName());

    @Override
    public void error(ErrorEvent errorEvent) {
        LOGGER.log(Level.SEVERE,"Something wrong happened {}", errorEvent);
        UI.getCurrent().access(() -> {
            LOGGER.log(Level.SEVERE,"An internal error has occurred." +
                    "Contact support for assistance. {0} ", new Object[]{errorEvent.getThrowable().getMessage()});
            Notification.show("An internal error has occurred." +
                    "Contact support for assistance. " + errorEvent.getThrowable().getMessage());
        });
    }

}