package com.rideshare.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "rides")
@Data
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime departureTime;

    @Column(nullable = false)
    private Integer seatsAvailable;

    private Double fare;

    // Helper method to update seats safely
    public void decrementSeats() {
        if (this.seatsAvailable > 0) {
            this.seatsAvailable--;
        } else {
            throw new IllegalStateException("No seats available!");
        }
    }
}
