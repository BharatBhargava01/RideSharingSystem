package com.rideshare.service;

import com.rideshare.database.DBConnection;
import com.rideshare.database.RideDAO;
import com.rideshare.model.Ride;
import com.rideshare.model.Booking;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Service
public class BookingService {

    private final RideDAO rideDAO = new RideDAO();

    public synchronized Booking bookRide(int rideId,
                                      int studentId) {

        try {
            // 1. Verify ride exists and has available seats
            Ride ride = rideDAO.getRideById(rideId);
            if (ride == null) {
                throw new IllegalArgumentException("Ride with ID " + rideId + " does not exist.");
            }

            if (ride.getSeats() <= 0) {
                throw new IllegalArgumentException("No seats available for ride with ID " + rideId + ".");
            }

            // 2. Decrement the ride's seats
            rideDAO.updateRideSeats(rideId, ride.getSeats() - 1);

            // 3. Generate a unique confirmation ID
            String confirmationId = "RS-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            // 4. Save booking
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO bookings(ride_id, student_id, status, confirmation_id) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, rideId);
            ps.setInt(2, studentId);
            ps.setString(3, "CONFIRMED");
            ps.setString(4, confirmationId);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int bookingId = 0;
            if (rs.next()) {
                bookingId = rs.getInt(1);
            }

            System.out.println("Ride Booked with Confirmation ID: " + confirmationId);
            return new Booking(bookingId, rideId, studentId, "CONFIRMED", confirmationId);

        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to book ride: " + e.getMessage(), e);
        }
    }

    public Booking getBookingByConfirmationId(String confirmationId) {
        if (confirmationId == null || confirmationId.trim().isEmpty()) {
            return null;
        }
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM bookings WHERE confirmation_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, confirmationId.trim());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("ride_id"),
                        rs.getInt("student_id"),
                        rs.getString("status"),
                        rs.getString("confirmation_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}