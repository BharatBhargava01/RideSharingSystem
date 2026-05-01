package com.rideshare.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Student entity. It extends User and adds Student-specific fields.
 */
@Entity
@DiscriminatorValue("STUDENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends User {

    private String branch;
    
    private Integer semester;

}