package com.example.HRM_Portal.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "team")
@Data
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID teamId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "team_leader", nullable = false)
    private String teamLeader;  // This will be the empId of the leader

    @ElementCollection
    @CollectionTable(name = "team_members", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "emp_id")
    private List<String> teamMembers;  // List of empIds of the team members

    @ManyToOne
    @JoinColumn(name = "business_id", referencedColumnName = "businessId", nullable = false)
    private OurUsers ourUsers;  // Reference to the business
}
