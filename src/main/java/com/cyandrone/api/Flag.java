package com.cyandrone.api;

import javax.inject.Singleton;

@Singleton
public class Flag {
    private static Flag ourInstance = new Flag();

    public static Flag getInstance() {
        return ourInstance;
    }

    private Flag() {
        messageArrived = false;
        messageArrivedId = null;
    }

    private Boolean messageArrived;
    private String messageArrivedId;

    public static Flag getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Flag ourInstance) {
        Flag.ourInstance = ourInstance;
    }

    public Boolean getMessageArrived() {
        return messageArrived;
    }

    public void setMessageArrived(Boolean messageArrived) {
        this.messageArrived = messageArrived;
    }

    public String getMessageArrivedId() {
        return messageArrivedId;
    }

    public void setMessageArrivedId(String messageArrivedId) {
        this.messageArrivedId = messageArrivedId;
    }
}
