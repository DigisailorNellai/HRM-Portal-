package com.example.HRM_Portal.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.method.P;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name="Tasks")
public class TaskManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summary;

    private String status;

    private String assignee;

    private LocalDate dueDate;

    private String projectName;

    @Column(nullable = false)
    private UUID businessId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;



    // Constructors

    public TaskManagementEntity(String summary, String status, String assignee, LocalDate dueDate,String projectName,UUID businessId) {
        this.summary = summary;
        this.status = status;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.projectName=projectName;
        this.businessId=businessId;
    }

}
