package com.example.HRM_Portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
@Entity
public class TaskManagementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summary;

    private String status;

    private String assignee;

    private LocalDate dueDate;

    // Constructors
    public TaskManagementEntity() {}

    public TaskManagementEntity(String summary, String status, String assignee, LocalDate dueDate) {
        this.summary = summary;
        this.status = status;
        this.assignee = assignee;
        this.dueDate = dueDate;
 }

}