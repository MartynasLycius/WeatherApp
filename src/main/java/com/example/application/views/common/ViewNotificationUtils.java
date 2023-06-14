package com.example.application.views.common;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public final class ViewNotificationUtils {
    private final static NotificationVariant DEFAULT_VARIANT = NotificationVariant.LUMO_SUCCESS;
    private final static Notification.Position DEFAULT_POSITION = Notification.Position.BOTTOM_CENTER;
    private final static int DEFAULT_DURATION = 2000;

    private ViewNotificationUtils() {

    }

    public static void showNotification(String message, Notification.Position position, NotificationVariant variant, int duration) {
        var notification = Notification.show(message);
        notification.setPosition(position);
        notification.addThemeVariants(variant);
        notification.setDuration(duration);
    }

    public static void showNotification(String message) {
        showNotification(message, DEFAULT_POSITION, DEFAULT_VARIANT, DEFAULT_DURATION);
    }

    public static void showNotification(String message, NotificationVariant variant) {
        showNotification(message, DEFAULT_POSITION, variant, DEFAULT_DURATION);
    }

    public static void showNotification(String message, Notification.Position position, NotificationVariant variant) {
       showNotification(message, position, variant, DEFAULT_DURATION);
    }

    public static void showErrorNotification(String message) {
        showNotification(message, Notification.Position.MIDDLE,  NotificationVariant.LUMO_ERROR);
    }
}
