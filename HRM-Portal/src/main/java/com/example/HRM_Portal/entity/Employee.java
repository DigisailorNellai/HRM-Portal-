package com.example.HRM_Portal.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employee")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "DS[0-9]{3}", message = "Employee ID must be in the format DS000 to DS999")
    private String empId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String dob; // Consider using LocalDate

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private Double baseSalary;

    @Column(nullable = false)
    private String address;

    @Lob
    @Column(name = "resume")
    private byte[] resume; // Store the resume as a byte array

    @Lob
    @Column(name = "aadhar_card")
    private byte[] aadharCard; // Store the Aadhar card as a byte array

    @Lob
    @Column(name = "pan_card")
    private byte[] panCard; // Store the PAN card as a byte array
}
