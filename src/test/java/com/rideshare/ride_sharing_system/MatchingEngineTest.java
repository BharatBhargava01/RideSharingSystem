package com.rideshare.ride_sharing_system;

import com.rideshare.model.Ride;
import com.rideshare.service.MatchingEngine;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchingEngineTest {

    @Test
    public void testSearchRide() {

        List<Ride> rides = new ArrayList<>();

        rides.add(new Ride(
                1,
                101,
                "Delhi",
                "Noida",
                3
        ));

        MatchingEngine engine =
                new MatchingEngine();

        List<Ride> result = engine.searchRide(
                rides,
                "Delhi",
                "Noida"
        );

        assertEquals(1, result.size());
    }
}