package com.nashid.weatherapp.core.notification;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NotificationUtils {
    public static void showMessage(String message, NotificationVariant notificationVariant) {
        Notification notification = new Notification();
        notification.addThemeVariants(notificationVariant);
        notification.setPosition(Notification.Position.TOP_CENTER);
        Div text = new Div(new Text(message));
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notification.close();
        });
        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        notification.setDuration(2000);
        notification.add(layout);
        notification.open();
    }

    public static void showSuccessMessage(String message) {
        showMessage(message, NotificationVariant.LUMO_SUCCESS);
    }

    public static void showErrorMessage(String message) {
        showMessage(message, NotificationVariant.LUMO_ERROR);
    }
}
