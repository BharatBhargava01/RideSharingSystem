package com.rideshare.repository;

import com.rideshare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA automatically implements this based on the method name
    Optional<User> findByEmail(String email);
}