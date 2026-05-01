package com.rideshare.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Abstract User class utilizing the Single Table Inheritance strategy.
 * All Users (Students, Drivers, Admins) will be stored in one "users" table,
 * differentiated by the "role" column.
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@Data // Lombok annotation to automatically generate Getters, Setters, etc.
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String password;

    // Common methods like login(), logout() would typically be handled
    // by Spring Security rather than embedded in the entity class.
}
