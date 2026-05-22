package com.rideshare.service;

import com.rideshare.model.Ride;
import java.util.ArrayList;
import java.util.List;

public class MatchingEngine {

    public List<Ride> searchRide(List<Ride> rides,
            String source,
            String destination) {

        List<Ride> matched = new ArrayList<>();

        for (Ride ride : rides) {

            if (ride.getSource().equalsIgnoreCase(source)
                    && ride.getDestination().equalsIgnoreCase(destination)) {

                matched.add(ride);
            }
        }

        return matched;
    }
}