package com.rideshare.service;

import com.rideshare.model.Notification;

public class NotificationService {

    public void notifyUser(String message) {

        Notification notification =
                new Notification(1, message);

        notification.sendNotification();
    }
}