package com.rideshare.model;

public class Student extends User {

    private String branch;

    public Student(int userId,
                   String name,
                   String email,
                   String phone,
                   String password,
                   String branch) {

        super(userId, name, email, phone, password);
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }
}