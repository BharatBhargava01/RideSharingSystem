package com.rideshare.database;

import com.rideshare.model.Ride;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RideDAO {

    public void addRide(Ride ride) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO rides(driver_id, source, destination, seats) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, ride.getDriverId());
            ps.setString(2, ride.getSource());
            ps.setString(3, ride.getDestination());
            ps.setInt(4, ride.getSeats());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Ride> getAllRides() {

        List<Ride> rides = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM rides");

            while (rs.next()) {

                Ride ride = new Ride(
                        rs.getInt("ride_id"),
                        rs.getInt("driver_id"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("seats")
                );

                rides.add(ride);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rides;
    }

    public void deleteRide(int id) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "DELETE FROM rides WHERE ride_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}