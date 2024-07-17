package org.weather.app.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import static com.vaadin.flow.component.button.ButtonVariant.LUMO_TERTIARY_INLINE;

public class NotificationComponent {

    public static Notification createNotification(String message, String type) {
        Notification notification = new Notification();


        Icon icon = new Icon();
        if(type.equalsIgnoreCase("success")){
            icon = VaadinIcon.CHECK_CIRCLE.create();
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }else {
            icon = VaadinIcon.WARNING.create();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }


        var layout = new HorizontalLayout(icon, new Text(message),
                createCloseBtn(notification));
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.add(layout);
        notification.setPosition(Notification.Position.TOP_END);
        notification.setDuration(5000);
        return notification;
    }

    public static Button createCloseBtn(Notification notification) {
        Button closeBtn = new Button(VaadinIcon.CLOSE_SMALL.create(),
                clickEvent -> notification.close());
        closeBtn.addThemeVariants(LUMO_TERTIARY_INLINE);

        return closeBtn;
    }
}
