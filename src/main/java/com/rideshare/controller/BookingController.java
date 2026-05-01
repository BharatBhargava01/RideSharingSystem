package com.rideshare.controller;

import com.rideshare.model.Booking;
import com.rideshare.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Endpoint: POST /api/bookings?rideId=5&studentId=2
    @PostMapping
    public ResponseEntity<Booking> bookRide(
            @RequestParam Long rideId,
            @RequestParam Long studentId) {

        Booking newBooking = bookingService.bookRide(rideId, studentId);
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }
}