package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.dto.ProjectDto;
import com.example.HRM_Portal.entity.ProjectEntity;
import com.example.HRM_Portal.service.ProjectService;
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
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjectsByBusinessId(@RequestParam("businessId") String businessId) {
        List<ProjectDto> projects = projectService.getProjectsByBusinessId(businessId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProjectByIdAndBusinessId(@PathVariable Long projectId, @RequestParam("businessId") String businessId) {
        Optional<ProjectDto> project = projectService.getProjectByIdAndBusinessId(projectId, businessId);
        return project.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody ProjectEntity project, @RequestParam("businessId") String businessId) {
        ProjectDto createdProject = projectService.saveProject(project, businessId);
        return ResponseEntity.ok(createdProject);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId, @RequestBody ProjectEntity updatedProject, @RequestParam("businessId") String businessId) {
        ProjectDto updatedProjectDto = projectService.updateProject(projectId, updatedProject, businessId);
        return ResponseEntity.ok(updatedProjectDto);
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId,@RequestParam("businessId") String businessId) {
        Optional<ProjectDto> project = projectService.deleteProjectByIdAndBusinessId(projectId,businessId);

        if (project.isPresent()) {
            projectService.deleteProjectByIdAndBusinessId(projectId,businessId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
