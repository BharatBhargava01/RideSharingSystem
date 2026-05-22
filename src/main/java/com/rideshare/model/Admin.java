package com.rideshare.model;

public class Admin extends User {

    public Admin(int userId,
                 String name,
                 String email,
                 String phone,
                 String password) {

        super(userId, name, email, phone, password);
    }

    public void monitorSystem() {
        System.out.println("Monitoring System");
    }
}