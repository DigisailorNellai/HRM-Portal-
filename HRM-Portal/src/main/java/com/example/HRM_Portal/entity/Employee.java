package com.example.HRM_Portal.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_id", unique = true, nullable = false)
    private String empId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "gender")
    private String gender;

    @Column(name = "department")
    private String department;

    @Column(name = "dob")
    private String dob;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "base_salary")
    private Double baseSalary;

    @Column(name = "address")
    private String address;

    @Column(name = "resume")
    @Lob
    private byte[] resume;

    @Column(name = "aadhar_card")
    @Lob
    private byte[] aadharCard;

    @Column(name = "pan_card")
    @Lob
    private byte[] panCard;

    @ElementCollection
    @CollectionTable(name = "collected_documents", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "document_name")
    private List<String> collectedDocuments;

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "businessId")
    private OurUsers ourUsers;

    // Getters and Setters
}
