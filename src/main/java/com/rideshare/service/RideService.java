package com.rideshare.service;

import com.rideshare.model.Driver;
import com.rideshare.model.Ride;
import com.rideshare.repository.RideRepository;
import com.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    public Ride addRide(Long driverId, Ride ride) {
        Driver driver = (Driver) userRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));
        
        ride.setDriver(driver);
        return rideRepository.save(ride);
    }

    public List<Ride> viewAllRides() {
        return rideRepository.findAll();
    }

    public void deleteRide(Long rideId) {
        rideRepository.deleteById(rideId);
    }
}