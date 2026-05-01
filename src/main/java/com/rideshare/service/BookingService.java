package com.rideshare.service;

import com.rideshare.model.Booking;
import com.rideshare.model.Ride;
import com.rideshare.model.Student;
import com.rideshare.repository.BookingRepository;
import com.rideshare.repository.RideRepository;
import com.rideshare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Executes the booking process.
     * The @Transactional annotation ensures that if anything fails (like out of
     * seats),
     * the entire database transaction is rolled back safely.
     */
    @Transactional
    public Booking bookRide(Long rideId, Long studentId) {
        // 1. Fetch the ride with a Pessimistic Lock to prevent concurrent
        // double-booking
        Ride ride = rideRepository.findByIdForUpdate(rideId)
                .orElseThrow(() -> new IllegalArgumentException("Ride not found"));

        // 2. Check seat availability
        if (ride.getSeatsAvailable() <= 0) {
            throw new IllegalStateException("Sorry, this ride is fully booked.");
        }

        // 3. Fetch the student
        Student student = (Student) userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        // 4. Create the booking
        Booking booking = new Booking();
        booking.setRide(ride);
        booking.setStudent(student);
        booking.setStatus("CONFIRMED");
        booking.setBookingDate(LocalDateTime.now());

        // 5. Update available seats on the ride
        ride.decrementSeats();

        // 6. Save changes to DB
        rideRepository.save(ride);
        Booking savedBooking = bookingRepository.save(booking);

        // 7. Trigger notification (Placeholder for Phase 5 Event-Driven Architecture)
        // rabbitTemplate.convertAndSend("notificationExchange", "booking.confirmed",
        // savedBooking.getBookingId());

        return savedBooking;
    }
}
