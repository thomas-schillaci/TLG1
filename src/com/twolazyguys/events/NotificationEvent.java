package com.twolazyguys.events;

import net.colozz.engine2.events.Event;

public class NotificationEvent extends Event {

    private String description;

    public NotificationEvent(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
