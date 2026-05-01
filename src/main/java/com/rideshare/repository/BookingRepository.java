package com.rideshare.repository;

import com.rideshare.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find all bookings for a specific student (Ride History)
    List<Booking> findByStudent_UserId(Long studentId);
}