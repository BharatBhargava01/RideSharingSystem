package com.rideshare.service;

import com.rideshare.database.RideDAO;
import com.rideshare.model.Ride;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class RideService {

    RideDAO rideDAO = new RideDAO();

    public Ride addRide(Long driverId, Ride ride) {
        Ride newRide = new Ride(ride.getRideId(), driverId.intValue(), ride.getSource(), ride.getDestination(), ride.getSeats());
        rideDAO.addRide(newRide);
        return newRide;
    }

    public void deleteRide(Long id) {
        rideDAO.deleteRide(id.intValue());
    }

    public List<Ride> getRides() {
        return rideDAO.getAllRides();
    }
}