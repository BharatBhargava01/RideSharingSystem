package com.rideshare.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("DRIVER")
@Data
@EqualsAndHashCode(callSuper = true)
public class Driver extends User {

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "license_no")
    private String licenseNo;

}