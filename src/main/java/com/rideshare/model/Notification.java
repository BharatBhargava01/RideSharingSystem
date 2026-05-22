package com.rideshare.model;

public class Notification {

    private int notificationId;
    private String message;

    public Notification(int notificationId,
            String message) {

        this.notificationId = notificationId;
        this.message = message;
    }

    public void sendNotification() {
        System.out.println(message);
    }

    public int getNotificationId(){
        return notificationId;
    }
}
