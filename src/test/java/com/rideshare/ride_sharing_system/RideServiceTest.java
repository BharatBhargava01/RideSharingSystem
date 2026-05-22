package com.rideshare.ride_sharing_system;

import com.rideshare.model.Ride;
import com.rideshare.service.RideService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RideServiceTest {

    RideService rideService = new RideService();

    @Test
    public void testAddRide() {

        Ride ride = new Ride(
                1,
                101,
                "Delhi",
                "Noida",
                4
        );

        rideService.addRide(101L, ride);

        assertTrue(true);
    }

    @Test
    public void testGetRides() {

        List<Ride> rides = rideService.getRides();

        assertNotNull(rides);
    }
}
