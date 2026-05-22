package com.rideshare.model;

public class Ride {

    private int rideId;
    private int driverId;
    private String source;
    private String destination;
    private int seats;

    public Ride(int rideId,
                int driverId,
                String source,
                String destination,
                int seats) {

        this.rideId = rideId;
        this.driverId = driverId;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
    }

    public synchronized boolean bookSeat() {

        if (seats > 0) {
            seats--;
            return true;
        }

        return false;
    }

    public int getRideId() {
        return rideId;
    }

    public int getDriverId(){
        return driverId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getSeats() {
        return seats;
    }
}
