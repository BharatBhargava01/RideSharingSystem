package com.rideshare.threads;

public class NotificationThread extends Thread {

    @Override
    public void run() {
        System.out.println("Notification Sent");
    }
}
