package com.rideshare.ride_sharing_system;

import org.junit.jupiter.api.Test;
import com.rideshare.service.BookingService;
import com.rideshare.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    BookingService bookingService = new BookingService();

    @Test
    public void testBookRide() {
        try {
            int driverId = createTestUser("test_driver", "Driver");
            int studentId = createTestUser("test_student", "Student");
            int rideId = createTestRide(driverId);

            assertNotEquals(-1, studentId);
            assertNotEquals(-1, rideId);

            bookingService.bookRide(rideId, studentId);
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void testBookRideReducesSeatsAndGeneratesConfirmationId() {
        try {
            int driverId = createTestUser("seat_driver", "Driver");
            int studentId = createTestUser("seat_student", "Student");
            int rideId = createTestRide(driverId); // Initial seats = 4

            com.rideshare.database.RideDAO rideDAO = new com.rideshare.database.RideDAO();
            com.rideshare.model.Ride initialRide = rideDAO.getRideById(rideId);
            assertEquals(4, initialRide.getSeats());

            com.rideshare.model.Booking booking = bookingService.bookRide(rideId, studentId);
            
            // 1. Verify confirmation ID is generated and starts with "RS-"
            assertNotNull(booking.getConfirmationId());
            assertTrue(booking.getConfirmationId().startsWith("RS-"));
            
            // 2. Verify available seats decreased from 4 to 3
            com.rideshare.model.Ride updatedRide = rideDAO.getRideById(rideId);
            assertEquals(3, updatedRide.getSeats());

            // 3. Verify we can fetch this booking by confirmation ID
            com.rideshare.model.Booking fetchedBooking = bookingService.getBookingByConfirmationId(booking.getConfirmationId());
            assertNotNull(fetchedBooking);
            assertEquals(booking.getBookingId(), fetchedBooking.getBookingId());
            assertEquals(booking.getConfirmationId(), fetchedBooking.getConfirmationId());
            assertEquals(rideId, fetchedBooking.getRideId());
            assertEquals(studentId, fetchedBooking.getStudentId());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Test failed due to exception: " + e.getMessage());
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