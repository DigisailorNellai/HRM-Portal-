package com.example.HRM_Portal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    private String name;
    private UUID businessId;
    private List<TaskManagementDto> tasks;
}
