package com.rideshare.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("ADMIN")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    // Admins might not have extra attributes in this basic design,
    // but the DiscriminatorValue ensures they are tagged as "ADMIN" in the
    // database.
}