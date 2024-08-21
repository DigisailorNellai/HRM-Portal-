package com.example.HRM_Portal.service;

import com.example.HRM_Portal.dto.ProjectDto;
import com.example.HRM_Portal.dto.TaskManagementDto;
import com.example.HRM_Portal.entity.ProjectEntity;
import com.example.HRM_Portal.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskManagementService taskService;

    public List<ProjectDto> getProjectsByBusinessId(String businessId) {
        return projectRepository.findByBusinessId(UUID.fromString(businessId))
                .stream()
                .map(this::convertToProjectDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDto> getProjectByIdAndBusinessId(Long projectId, String businessId) {
        return projectRepository.findByIdAndBusinessId(projectId, UUID.fromString(businessId))
                .map(this::convertToProjectDTO);
    }

    public ProjectDto saveProject(ProjectEntity project, String businessId) {
        project.setBusinessId(UUID.fromString(businessId));
        ProjectEntity savedProject = projectRepository.save(project);
        return convertToProjectDTO(savedProject);
    }

    public ProjectDto updateProject(Long projectId, ProjectEntity updatedProject, String businessId) {
        updatedProject.setBusinessId(UUID.fromString(businessId));
        ProjectEntity savedProject = projectRepository.save(updatedProject);
        return convertToProjectDTO(savedProject);
    }

    public Optional<ProjectDto> deleteProjectByIdAndBusinessId(Long projectId, String businessId) {
        UUID businessUUID = UUID.fromString(businessId);

        Optional<ProjectEntity> projectEntity = projectRepository.findByIdAndBusinessId(projectId, businessUUID);

        // If project exists, delete it
        if (projectEntity.isPresent()) {
            projectRepository.delete(projectEntity.get());
            // Convert deleted project entity to DTO and return it
            return Optional.of(convertToProjectDTO(projectEntity.get()));
        } else {
            return Optional.empty(); // Project not found
        }
    }

    private ProjectDto convertToProjectDTO(ProjectEntity project) {
        List<TaskManagementDto> tasks = project.getTasks()
                .stream()
                .map(taskService::convertToTaskDTO)
                .collect(Collectors.toList());

        return new ProjectDto(project.getId(), project.getName(), project.getBusinessId(), tasks);
    }
}
