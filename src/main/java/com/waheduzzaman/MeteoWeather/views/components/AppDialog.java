package com.waheduzzaman.MeteoWeather.views.components;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.waheduzzaman.MeteoWeather.interfaces.AddClickListener;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class AppDialog extends Div {
    private final ConfirmDialog dialog = new ConfirmDialog();
    private String titleLabel;
    private String message;
    private String cancelButtonLabel, confirmButtonLabel;
    private boolean showCancelButton = false, showConfirmButton = false, cancelable = false;

    private AddClickListener cancelClickListener, confirmClickListener;

    public AppDialog() {
        dialog.setCancelable(cancelable);
    }

    public void show() {
        if (validateRequirements()) {
            build();
            dialog.open();
        }
    }

    private void build() {
        dialog.setHeader(titleLabel);
        if (StringUtils.isNotEmpty(message))
            dialog.setText(message);
        if (showCancelButton) {
            dialog.setRejectable(showCancelButton);
            dialog.setRejectText(StringUtils.isEmpty(cancelButtonLabel) ? "Cancel" : cancelButtonLabel);
            dialog.addRejectListener(event -> cancelClickListener.onItemClick(null));
        }
        if (showConfirmButton) {
            dialog.setConfirmText(StringUtils.isEmpty(confirmButtonLabel) ? "Confirm" : confirmButtonLabel);
            dialog.addConfirmListener(event -> {
                System.out.println("event out: " + event.toString());
                confirmClickListener.onItemClick(null);
            });
        }
    }

    private boolean validateRequirements() {
        if (StringUtils.isEmpty(titleLabel))
            Notification.show("Title can not be empty").addThemeVariants(NotificationVariant.LUMO_ERROR);

        if (showCancelButton && (cancelClickListener == null))
            Notification.show("Cancel click listener can not be empty").addThemeVariants(NotificationVariant.LUMO_ERROR);

        if (showConfirmButton && (confirmClickListener == null))
            Notification.show("Confirm click listener can not be empty").addThemeVariants(NotificationVariant.LUMO_ERROR);

        return StringUtils.isNotEmpty(titleLabel) ||
                (showCancelButton && (cancelClickListener != null)) ||
                (showConfirmButton && (confirmClickListener != null));
    }

}