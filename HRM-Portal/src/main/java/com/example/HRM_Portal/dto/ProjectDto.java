package com.example.HRM_Portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private List<TaskManagementDto> tasks;
}
