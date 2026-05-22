package com.rideshare.model;

public class Booking {

    private int bookingId;
    private int rideId;
    private int studentId;
    private String status;

    public Booking(int bookingId,
                   int rideId,
                   int studentId,
                   String status) {

        this.bookingId = bookingId;
        this.rideId = rideId;
        this.studentId = studentId;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getRideId() {
        return rideId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStatus() {
        return status;
    }
}