package com.rideshare.model;

public class Driver extends User {

    private String vehicleNumber;

    public Driver(int userId,
                  String name,
                  String email,
                  String phone,
                  String password,
                  String vehicleNumber) {

        super(userId, name, email, phone, password);
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }
}