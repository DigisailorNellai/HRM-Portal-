package com.example.HRM_Portal.service;

import com.example.HRM_Portal.dto.ProjectDto;
import com.example.HRM_Portal.dto.TaskManagementDto;
import com.example.HRM_Portal.entity.ProjectEntity;
import com.example.HRM_Portal.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskManagementService taskService;

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::convertToProjectDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProjectDto> getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .map(this::convertToProjectDTO);
    }

    public ProjectDto saveProject(ProjectEntity project) {
        ProjectEntity savedProject = projectRepository.save(project);
        return convertToProjectDTO(savedProject);
    }
    public ProjectDto updateProject(Long projectId, ProjectEntity updatedProject) {
        // Save updated project directly (assuming projectId is set in the controller)
        ProjectEntity savedProject = projectRepository.save(updatedProject);
        return convertToProjectDTO(savedProject);
    }

    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }



    private ProjectDto convertToProjectDTO(ProjectEntity project) {
        List<TaskManagementDto> tasks = project.getTasks()
                .stream()
                .map(taskService::convertToTaskDTO)
                .collect(Collectors.toList());

        return new ProjectDto(project.getId(), project.getName(), tasks);
    }
}

