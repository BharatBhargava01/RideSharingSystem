package com.rideshare.threads;

import com.rideshare.service.BookingService;

public class BookingThread extends Thread {

    BookingService bookingService;
    int rideId;
    int studentId;

    public BookingThread(BookingService bookingService,
                         int rideId,
                         int studentId) {

        this.bookingService = bookingService;
        this.rideId = rideId;
        this.studentId = studentId;
    }

    @Override
    public void run() {
        bookingService.bookRide(rideId, studentId);
    }
}
