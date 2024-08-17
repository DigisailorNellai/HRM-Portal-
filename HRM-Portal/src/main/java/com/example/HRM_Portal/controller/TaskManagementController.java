package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.dto.ProjectDto;
import com.example.HRM_Portal.dto.TaskManagementDto;
import com.example.HRM_Portal.entity.ProjectEntity;
import com.example.HRM_Portal.entity.TaskManagementEntity;
import com.example.HRM_Portal.service.ProjectService;
import com.example.HRM_Portal.service.TaskManagementService;
import com.example.HRM_Portal.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskManagementController {

    @Autowired
    private TaskManagementService taskService;
    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<TaskManagementDto>> getTasksByProjectId(@PathVariable Long projectId) {
        List<TaskManagementDto> tasks = taskService.getTasksByProjectId(projectId);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskManagementDto> getTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        Optional<TaskManagementDto> task = taskService.getTaskById(taskId);
        if (task.isPresent() && task.get().getProjectId().equals(projectId)) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TaskManagementDto> createTask(@PathVariable Long projectId, @RequestBody TaskManagementEntity task) {
        ProjectEntity project = new ProjectEntity(projectId);
        task.setProject(project);
        Optional<ProjectDto> projectData = taskService.getProjectById(projectId);
//        ProjectEntity project1 = new ProjectEntityName();
//        task.setProjectName(project2.name);
        TaskManagementDto createdTask = taskService.saveTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

    }
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskManagementDto> updateTask(@PathVariable Long projectId,
                                                        @PathVariable Long taskId,
                                                        @RequestBody TaskManagementEntity updatedTask) {
        Optional<TaskManagementDto> existingTask = taskService.getTaskById(taskId);

        if (existingTask.isPresent() && existingTask.get().getProjectId().equals(projectId)) {
            updatedTask.setId(taskId);
            updatedTask.setProject(new ProjectEntity(projectId)); // Set the project
            TaskManagementDto savedTask = taskService.saveTask(updatedTask);
            return new ResponseEntity<>(savedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        Optional<TaskManagementDto> task = taskService.getTaskById(taskId);

        if (task.isPresent() && task.get().getProjectId().equals(projectId)) {
            taskService.deleteTask(taskId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}