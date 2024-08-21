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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskManagementService {
    @Autowired
    private TaskManagementRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<TaskManagementDto> getTasksByProjectIdAndBusinessId(Long projectId, String businessId) {
        return taskRepository.findByProjectIdAndBusinessId(projectId, UUID.fromString(businessId))
                .stream()
                .map(this::convertToTaskDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskManagementDto> getTaskByIdAndBusinessId(Long taskId, String businessId) {
        return taskRepository.findByIdAndBusinessId(taskId, UUID.fromString(businessId))
                .map(this::convertToTaskDTO);
    }

    public TaskManagementDto saveTask(TaskManagementEntity task, String businessId) {
        task.setBusinessId(UUID.fromString(businessId));
        TaskManagementEntity savedTask = taskRepository.save(task);
        return convertToTaskDTO(savedTask);
    }

    public TaskManagementDto updateTask(Long taskId, TaskManagementEntity updatedTask, String businessId) {
        updatedTask.setBusinessId(UUID.fromString(businessId));
        TaskManagementEntity savedTask = taskRepository.save(updatedTask);
        return convertToTaskDTO(savedTask);
    }
    public void deleteTaskByIdAndBusinessId(Long taskId, String businessId) {
        UUID businessUUID = UUID.fromString(businessId);

        // Fetch the task by ID and business ID
        Optional<TaskManagementEntity> taskEntity = taskRepository.findByIdAndBusinessId(taskId, businessUUID);

        // If the task exists, delete it
        taskEntity.ifPresent(taskRepository::delete);
    }




    TaskManagementDto convertToTaskDTO(TaskManagementEntity task) {
        return new TaskManagementDto(
                task.getId(),
                task.getSummary(),
                task.getStatus(),
                task.getAssignee(),
                task.getDueDate(),
                task.getProject().getId(),
                task.getProject().getName(),// Ensure the project ID (Long) is used here
                task.getProject().getBusinessId()  // Use this for UUID type


        );
    }
}
