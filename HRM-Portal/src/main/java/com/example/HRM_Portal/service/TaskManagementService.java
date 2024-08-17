package com.example.HRM_Portal.service;

import com.example.HRM_Portal.dto.ProjectDto;
import com.example.HRM_Portal.dto.TaskManagementDto;
import com.example.HRM_Portal.entity.ProjectEntity;
import com.example.HRM_Portal.entity.TaskManagementEntity;
import com.example.HRM_Portal.repository.ProjectRepository;
import com.example.HRM_Portal.repository.TaskManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskManagementService {
    @Autowired
    private TaskManagementRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<TaskManagementDto> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskManagementDto> getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .map(this::convertToTaskDTO);
    }

    public TaskManagementDto saveTask(TaskManagementEntity task) {
        TaskManagementEntity savedTask = taskRepository.save(task);
        return convertToTaskDTO(savedTask);
    }

    public Optional<ProjectDto> getProjectById(Long projectId) {
        return projectRepository.findById(projectId)
                .map(this::convertToProjectDTO);
    }

    public ProjectDto saveProject(ProjectEntity project) {
        ProjectEntity savedProject = projectRepository.save(project);
        return convertToProjectDTO(savedProject);
    }
    public TaskManagementDto updateTask(Long taskId, TaskManagementEntity updatedTask) {
        // Save updated task directly (assuming taskId is set in the controller)
        TaskManagementEntity savedTask = taskRepository.save(updatedTask);
        return convertToTaskDTO(savedTask);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public TaskManagementDto convertToTaskDTO(TaskManagementEntity task) {
        return new TaskManagementDto(
                task.getId(),
                task.getSummary(),
                task.getStatus(),
                task.getAssignee(),
                task.getDueDate(),
                task.getProject().getId(),
                task.getProject().getName()
        );
    }

    private ProjectDto convertToProjectDTO(ProjectEntity project) {
        List<TaskManagementDto> tasks = project.getTasks()
                .stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());

        return new ProjectDto(project.getId(), project.getName(), tasks);
    }
}

