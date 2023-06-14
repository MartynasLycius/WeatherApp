package com.example.application.views.common;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public final class ViewNotificationUtils {
    private final static int DEFAULT_DURATION = 2000;

    public static void showNotification(String message, Notification.Position position, NotificationVariant variant, int duration) {
        var notification = Notification.show(message);
        notification.setPosition(position);
        notification.addThemeVariants(variant);
        notification.setDuration(duration);
    }

    public static void showNotification(String message) {
        showNotification(message, Notification.Position.BOTTOM_CENTER, NotificationVariant.LUMO_SUCCESS, DEFAULT_DURATION);
    }

    public static void showErrorNotification(String message) {
        showNotification(message, Notification.Position.MIDDLE,  NotificationVariant.LUMO_ERROR, DEFAULT_DURATION);
    }
}
