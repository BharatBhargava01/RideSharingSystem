package com.rideshare.exception;

// Custom exception mapped to a specific business rule failure
public class SeatUnavailableException extends RuntimeException {
    public SeatUnavailableException(String message) {
        super(message);
    }
}