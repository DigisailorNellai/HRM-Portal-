package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.TaskManagementEntity;
import com.example.HRM_Portal.repository.TaskManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskManagementService {

    @Autowired
    private TaskManagementRepository taskManagementRepository;

    public List<TaskManagementEntity> getAllTasks() {
        return taskManagementRepository.findAll();
    }

    public Optional<TaskManagementEntity> getTaskById(Long id) {
        return taskManagementRepository.findById(id);
    }

    public TaskManagementEntity createTask(TaskManagementEntity task) {
        return taskManagementRepository.save(task);
    }

    public TaskManagementEntity updateTask(Long id, TaskManagementEntity taskDetails) {
        TaskManagementEntity task = taskManagementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setSummary(taskDetails.getSummary());
        task.setStatus(taskDetails.getStatus());
        task.setAssignee(taskDetails.getAssignee());
        task.setDueDate(taskDetails.getDueDate());
        return taskManagementRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskManagementRepository.deleteById(id);
 }
}