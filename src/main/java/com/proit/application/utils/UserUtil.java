package com.proit.application.utils;

import com.vaadin.flow.server.VaadinSession;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;

@Slf4j
public final class UserUtil {
    private UserUtil() {

    }

    public static String getUserTimezone() {
        log.debug("Retrieving user's timezone");

        VaadinSession vaadinSession = VaadinSession.getCurrent();
        ZoneId userTimeZone = vaadinSession.getAttribute(ZoneId.class);
        return userTimeZone.toString();
    }
}
