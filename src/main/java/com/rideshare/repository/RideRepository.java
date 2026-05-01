package com.rideshare.repository;

import com.rideshare.model.Ride;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    /**
     * Phase 4: Solving Concurrency.
     * This custom query fetches a Ride and applies a PESSIMISTIC_WRITE lock on the
     * row in PostgreSQL.
     * This ensures that if two students try to book the last seat at the exact same
     * millisecond,
     * the database forces one transaction to wait until the first is completed.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM Ride r WHERE r.rideId = :rideId")
    Optional<Ride> findByIdForUpdate(@Param("rideId") Long rideId);

}