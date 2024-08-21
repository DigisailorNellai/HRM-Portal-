package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.dto.TaskManagementDto;
import com.example.HRM_Portal.entity.ProjectEntity;
import com.example.HRM_Portal.entity.TaskManagementEntity;
import com.example.HRM_Portal.service.TaskManagementService;
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

    @GetMapping
    public ResponseEntity<List<TaskManagementDto>> getTasksByProjectIdAndBusinessId(@PathVariable Long projectId, @RequestParam("businessId") String businessId) {
        List<TaskManagementDto> tasks = taskService.getTasksByProjectIdAndBusinessId(projectId, businessId);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskManagementDto> getTaskByIdAndBusinessId(@PathVariable Long projectId, @PathVariable Long taskId, @RequestParam("businessId") String businessId) {
        Optional<TaskManagementDto> task = taskService.getTaskByIdAndBusinessId(taskId, businessId);
        if (task.isPresent() && task.get().getProjectId().equals(projectId)) {
            return new ResponseEntity<>(task.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TaskManagementDto> createTask(@PathVariable Long projectId, @RequestBody TaskManagementEntity task, @RequestParam("businessId") String businessId) {
        task.setProject(new ProjectEntity(projectId));
        TaskManagementDto createdTask = taskService.saveTask(task, businessId);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskManagementDto> updateTask(@PathVariable Long taskId, @RequestBody TaskManagementEntity updatedTask, @RequestParam("businessId") String businessId) {
        TaskManagementDto updated = taskService.updateTask(taskId, updatedTask, businessId);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long projectId, @PathVariable Long taskId,@RequestParam ("businessId") String businessId) {
        Optional<TaskManagementDto> task = taskService.getTaskByIdAndBusinessId(taskId,businessId);

        if (task.isPresent() && task.get().getProjectId().equals(projectId)) {
            taskService.deleteTaskByIdAndBusinessId(taskId,businessId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
