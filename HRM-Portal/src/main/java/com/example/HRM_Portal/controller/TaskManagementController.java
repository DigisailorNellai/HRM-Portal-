package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.entity.TaskManagementEntity;
import com.example.HRM_Portal.service.TaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskManagementController {

    @Autowired
    private TaskManagementService taskManagementService;

    @GetMapping
    public List<TaskManagementEntity> getAllTasks() {
        return taskManagementService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskManagementEntity> getTaskById(@PathVariable Long id) {
        Optional<TaskManagementEntity> task = taskManagementService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TaskManagementEntity createTask(@RequestBody TaskManagementEntity task) {
        return taskManagementService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskManagementEntity> updateTask(@PathVariable Long id, @RequestBody TaskManagementEntity taskDetails) {
        TaskManagementEntity updatedTask = taskManagementService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskManagementService.deleteTask(id);
        return ResponseEntity.noContent().build();
 }
}