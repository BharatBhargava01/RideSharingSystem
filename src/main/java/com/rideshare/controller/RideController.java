package com.rideshare.controller;

import com.rideshare.model.Ride;
import com.rideshare.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    // Endpoint: POST /api/rides?driverId=1
    @PostMapping
    public ResponseEntity<Ride> createRide(@RequestParam Long driverId, @RequestBody Ride ride) {
        Ride createdRide = rideService.addRide(driverId, ride);
        return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
    }

    // Endpoint: GET /api/rides
    @GetMapping
    public ResponseEntity<List<Ride>> getAllRides() {
        return ResponseEntity.ok(rideService.viewAllRides());
    }

    // Endpoint: DELETE /api/rides/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRide(@PathVariable Long id) {
        rideService.deleteRide(id);
        return ResponseEntity.ok("Ride deleted successfully");
    }
}