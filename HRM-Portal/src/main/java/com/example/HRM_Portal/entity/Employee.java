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

    @Column(name = "resume_path")
    private String resumePath; // Store the resume file path

    @Column(name = "aadhar_card_path")
    private String aadharCardPath; // Store the Aadhar card file path

    @Column(name = "pan_card_path")
    private String panCardPath; // Store the PAN card file path
}
