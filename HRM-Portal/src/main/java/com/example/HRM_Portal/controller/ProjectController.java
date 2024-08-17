package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.dto.ProjectDto;
import com.example.HRM_Portal.entity.ProjectEntity;
import com.example.HRM_Portal.service.ProjectService;
import com.example.HRM_Portal.service.TaskManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private TaskManagementService taskService;
    @Autowired
    private ProjectService projectService;


    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long projectId) {
        Optional<ProjectDto> project = taskService.getProjectById(projectId);
        return project.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectEntity project) {
        ProjectDto createdProject = taskService.saveProject(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId,
                                                    @RequestBody ProjectEntity updatedProject) {
        Optional<ProjectDto> existingProject = projectService.getProjectById(projectId);

        if (existingProject.isPresent()) {
            updatedProject.setId(projectId);
            ProjectDto savedProject = projectService.saveProject(updatedProject);
            return new ResponseEntity<>(savedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        Optional<ProjectDto> project = projectService.getProjectById(projectId);

        if (project.isPresent()) {
            projectService.deleteProject(projectId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}