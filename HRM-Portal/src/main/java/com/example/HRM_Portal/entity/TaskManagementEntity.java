package com.example.HRM_Portal.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.method.P;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    // Constructors
    public TaskManagementEntity() {}

    public TaskManagementEntity(String summary, String status, String assignee, LocalDate dueDate,String projectName) {
        this.summary = summary;
        this.status = status;
        this.assignee = assignee;
        this.dueDate = dueDate;
        this.projectName=projectName;
    }

}
