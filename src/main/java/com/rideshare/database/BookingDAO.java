package com.rideshare.database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookingDAO {

    public void createBooking(int rideId,
                              int studentId) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "INSERT INTO bookings(ride_id, student_id, status) VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, rideId);
            ps.setInt(2, studentId);
            ps.setString(3, "CONFIRMED");

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
