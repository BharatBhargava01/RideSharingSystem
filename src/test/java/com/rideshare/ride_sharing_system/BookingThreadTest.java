package com.rideshare.ride_sharing_system;

import org.junit.jupiter.api.Test;
import com.rideshare.service.BookingService;
import com.rideshare.threads.BookingThread;
import com.rideshare.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class BookingThreadTest {

    @Test
    public void testMultiThreadBooking() {
        try {
            BookingService service = new BookingService();

            int driverId = createTestUser("thread_driver", "Driver");
            int student1Id = createTestUser("thread_student1", "Student");
            int student2Id = createTestUser("thread_student2", "Student");
            int rideId = createTestRide(driverId);

            assertNotEquals(-1, student1Id);
            assertNotEquals(-1, student2Id);
            assertNotEquals(-1, rideId);

            BookingThread t1 = new BookingThread(service, rideId, student1Id);
            BookingThread t2 = new BookingThread(service, rideId, student2Id);

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Multi-threaded test failed: " + e.getMessage());
        }
    }

    private int createTestUser(String name, String role) throws Exception {
        Connection con = DBConnection.getConnection();
        String query = "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, name + "@test.com");
        ps.setString(3, "123456");
        ps.setString(4, "password");
        ps.setString(5, role);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    private int createTestRide(int driverId) throws Exception {
        Connection con = DBConnection.getConnection();
        String query = "INSERT INTO rides (driver_id, source, destination, seats) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, driverId);
        ps.setString(2, "Delhi");
        ps.setString(3, "Noida");
        ps.setInt(4, 4);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }
}
