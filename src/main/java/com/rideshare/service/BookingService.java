package com.rideshare.service;

import com.rideshare.database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.rideshare.model.Booking;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    public synchronized Booking bookRide(int rideId,
                                      int studentId) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO bookings(ride_id, student_id, status) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, rideId);
            ps.setInt(2, studentId);
            ps.setString(3, "CONFIRMED");

            ps.executeUpdate();

            System.out.println("Ride Booked");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to book ride: " + e.getMessage(), e);
        }
        return new Booking(0, rideId, studentId, "CONFIRMED");
    }
}