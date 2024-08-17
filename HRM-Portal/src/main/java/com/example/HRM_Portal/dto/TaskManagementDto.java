package com.example.HRM_Portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TaskManagementDto {
    private Long id;
    private String summary;
    private String status;
    private String assignee;
    private LocalDate dueDate;
    private Long projectId;
    private String projectname;
}
