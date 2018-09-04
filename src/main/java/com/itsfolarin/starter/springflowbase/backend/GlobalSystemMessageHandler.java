package com.itsfolarin.starter.springflowbase.backend;

import com.vaadin.flow.server.CustomizedSystemMessages;

public class GlobalSystemMessageHandler extends CustomizedSystemMessages {

    public GlobalSystemMessageHandler() {
        setSessionExpiredNotificationEnabled(false);
    }
}
